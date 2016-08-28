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
public class UserDto {
    private String username;
    private String password;
    private String passwordConfirm;
}
