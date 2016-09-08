package com.robotnec.chords.web.dto;

import lombok.*;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChordsUserDto {
    private Long id;
    private String name;
    private List<String> authorities;
}
