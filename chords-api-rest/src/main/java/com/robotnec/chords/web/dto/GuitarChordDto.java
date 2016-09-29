package com.robotnec.chords.web.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GuitarChordDto {
    private long id;

    @Pattern(regexp = "\\b[A-G](?:(?:add|dim|aug|maj|mM|mMaj|sus|m|b|#|\\d)?(?:/[A-G0-9])?)*(?!\\||—|-|\\.|:)(?:\\b|#)+")
    private String name;

    @Pattern(regexp = "^[0-9xX]{6}$")
    private String diagram;
}
