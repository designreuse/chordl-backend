package com.robotnec.chords.web.dto;

import lombok.*;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenDto {
    private String jwtToken;
}
