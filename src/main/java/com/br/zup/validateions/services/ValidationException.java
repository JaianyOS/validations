package com.br.zup.validateions.services;

import java.util.List;

public class ValidationException extends RuntimeException{
    private final List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors){
        this.errors = errors;
    }
    public List<ValidationError> getErrors(){
        return errors;
    }
}
