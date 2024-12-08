package com.kurtlobato.challenge.exception;

import com.kurtlobato.challenge.validator.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse<List<ValidationError>>> handleValidationException(final ValidationException exception) {
        return ResponseEntity.status(exception.getCode()).body(new ErrorResponse<>(exception.getCode().toString(), exception.getMessage(), exception.getValidationErrors()));
    }

    static class ErrorResponse<T> {

        private String code;
        private String message;
        private T body;

        public ErrorResponse() {
        }

        public ErrorResponse(String code, String message, T body) {
            this.code = code;
            this.message = message;
            this.body = body;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public T getBody() {
            return body;
        }
    }
}
