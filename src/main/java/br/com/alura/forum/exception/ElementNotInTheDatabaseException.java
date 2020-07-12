package br.com.alura.forum.exception;

import org.springframework.validation.FieldError;

public class ElementNotInTheDatabaseException extends RuntimeException{
    private final FieldError fieldError;
    private static final String defaultMessage = "The value searched is not present in the database";

    public ElementNotInTheDatabaseException(FieldError fieldError) {
        super(fieldError.getDefaultMessage());
        this.fieldError = fieldError;
    }

    public ElementNotInTheDatabaseException(String objectName, String field) {
        super(defaultMessage);
        this.fieldError = new FieldError(objectName, field, defaultMessage);
    }

    public FieldError getFieldError() {
        return fieldError;
    }
}
