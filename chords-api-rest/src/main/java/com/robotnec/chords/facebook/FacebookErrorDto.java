package com.robotnec.chords.facebook;

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
public class FacebookErrorDto {
    private int code;
    private String message;
    private int subcode;
}
