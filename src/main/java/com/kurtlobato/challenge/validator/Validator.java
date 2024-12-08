package com.kurtlobato.challenge.validator;

import java.util.List;

public interface Validator<T> {

    List<ValidationError> validate(T objToValidate);

    default ValidationError buildError(String field, ValidationErrorType type, String message) {
        return new ValidationError(message, field, type);
    }

}
