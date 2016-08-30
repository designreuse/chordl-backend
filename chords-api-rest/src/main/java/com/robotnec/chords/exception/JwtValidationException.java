package com.robotnec.chords.exception;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);
    }
}
