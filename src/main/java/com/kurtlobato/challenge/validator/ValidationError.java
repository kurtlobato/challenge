package com.kurtlobato.challenge.validator;

public class ValidationError {

    public ValidationError() {
    }

    public ValidationError(String message, String field, ValidationErrorType type) {
        this.message = message;
        this.field = field;
        this.type = type;
    }

    public String message;
    public String field;
    public ValidationErrorType type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ValidationErrorType getType() {
        return type;
    }

    public void setType(ValidationErrorType type) {
        this.type = type;
    }
}
