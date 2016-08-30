package com.robotnec.chords.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Getter
public class InvalidRequestException extends RuntimeException {

    private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public InvalidRequestException(Errors errors) {
        this("Invalid request", errors);
    }
}