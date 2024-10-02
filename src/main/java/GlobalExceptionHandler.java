package com.br.zup.validateions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleValidationException(ValidationException ex) {
        List<ValidationErrorResponse> errorResponses = ex.getErrors().stream()
                .map(error -> new ValidationErrorResponse(error.getField(), error.getMessages()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponses);
    }

    public static class ValidationErrorResponse {
        private String field;
        private List<String> errors;

        public ValidationErrorResponse(String field, List<String> errors) {
            this.field = field;
            this.errors = errors;
        }

        public String getField() {
            return field;
        }

        public List<String> getErrors() {
            return errors;
        }
    }
}
