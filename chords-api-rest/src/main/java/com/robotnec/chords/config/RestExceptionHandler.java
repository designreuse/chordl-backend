package com.robotnec.chords.config;

import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.web.dto.ErrorResourceDto;
import com.robotnec.chords.web.dto.FieldErrorResourceDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ InvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResourceDto> fieldErrorResourceDtos = new ArrayList<>();

        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResourceDto fieldErrorResourceDto = new FieldErrorResourceDto();
            fieldErrorResourceDto.setResource(fieldError.getObjectName());
            fieldErrorResourceDto.setField(fieldError.getField());
            fieldErrorResourceDto.setCode(fieldError.getCode());
            fieldErrorResourceDto.setMessage(fieldError.getDefaultMessage());
            fieldErrorResourceDtos.add(fieldErrorResourceDto);
        }

        ErrorResourceDto error = new ErrorResourceDto("InvalidRequest", ire.getMessage());
        error.setFieldErrors(fieldErrorResourceDtos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}