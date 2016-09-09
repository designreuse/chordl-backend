package com.robotnec.chords.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(long resourceId) {
        super(String.format("Entity with id '%s' not found", resourceId));
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("Entity with name '%s' not found", resourceName));
    }

    public ResourceNotFoundException(String resourceName, long resourceId) {
        super(String.format("Entity '%s' with id '%s' not found", resourceName, resourceId));
    }
}
