package com.robotnec.chords.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResourceDto {
    private String code;
    private String message;
    private List<FieldErrorResourceDto> fieldErrors;

    public ErrorResourceDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}