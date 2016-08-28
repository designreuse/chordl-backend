package com.robotnec.chords.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FieldErrorResourceDto {
    private String resource;
    private String field;
    private String code;
    private String message;
}