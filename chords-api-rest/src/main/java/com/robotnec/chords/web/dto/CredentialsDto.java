package com.robotnec.chords.web.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CredentialsDto {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String socialToken;
}
