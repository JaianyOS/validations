package com.br.zup.validateions.services;

import com.br.zup.validateions.controllers.dtos.UserRegisterDTO;
import com.br.zup.validateions.exceptions.ValidationError;
import com.br.zup.validateions.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    public void validateUser(UserRegisterDTO user) {
        List<ValidationError> errors = new ArrayList<>();

        // Validação do nome
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            ValidationError error = new ValidationError("name");
            error.addMessage("Nome é obrigatório");
            errors.add(error);
        } else {
            String[] nameParts = user.getName().trim().split("\\s+");
            if (nameParts.length < 2) {
                ValidationError error = new ValidationError("name");
                error.addMessage("Nome deve conter nome e sobrenome");
                errors.add(error);
            }
        }

        // Validação da idade
        if (user.getAge() <= 0) {
            ValidationError error = new ValidationError("age");
            error.addMessage("Idade não pode ser 0");
            errors.add(error);
        } else if (user.getAge() < 18) {
            ValidationError error = new ValidationError("age");
            error.addMessage("Idade deve ser maior que 18 anos");
            errors.add(error);
        } else if (user.getAge() > 110) {
            ValidationError error = new ValidationError("age");
            error.addMessage("Idade deve ser menor que 110 anos");
            errors.add(error);
        }

        // Validação do ano de nascimento
        int currentYear = LocalDate.now().getYear();
        if (user.getYearOfBirth() <= 0) {
            ValidationError error = new ValidationError("yearOfBirth");
            error.addMessage("Ano de nascimento é obrigatório");
            errors.add(error);
        } else if (currentYear - user.getYearOfBirth() > 100) {
            ValidationError error = new ValidationError("yearOfBirth");
            error.addMessage("Ano de nascimento deve ser maior que 100 anos atrás");
            errors.add(error);
        } else if (user.getYearOfBirth() == currentYear) {
            ValidationError error = new ValidationError("yearOfBirth");
            error.addMessage("Ano de nascimento não pode ser o ano atual");
            errors.add(error);
        } else if (currentYear - user.getAge() != user.getYearOfBirth()) {
            ValidationError error = new ValidationError("yearOfBirth");
            error.addMessage("Ano de nascimento não corresponde à idade informada");
            errors.add(error);
        }

        // Validação do e-mail
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            ValidationError error = new ValidationError("email");
            error.addMessage("Email é obrigatório");
            errors.add(error);
        } else {
            String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            Pattern pattern = Pattern.compile(emailRegex);
            if (!pattern.matcher(user.getEmail()).matches()) {
                ValidationError error = new ValidationError("email");
                error.addMessage("Email não está no formato correto");
                errors.add(error);
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public UserDTO save(UserRegisterDTO user) {
        validateUser(user);
        // salvar o usuário 
        return new UserDTO(user.getName(), user.getAge(), user.getYearOfBirth(), user.getEmail());
    }
}
