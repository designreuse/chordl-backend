package com.robotnec.chords.web.dto;

import lombok.*;

import java.util.Date;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrettyDiffDto {
    private String diff;
}
