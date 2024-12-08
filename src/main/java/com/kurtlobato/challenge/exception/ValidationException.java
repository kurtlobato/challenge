package com.kurtlobato.challenge.exception;

import com.kurtlobato.challenge.validator.ValidationError;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ValidationError> validationErrors;
    private final Integer code = 400;

    public ValidationException(String message, List<ValidationError> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public Integer getCode() {
        return code;
    }
}
