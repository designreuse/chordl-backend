package com.robotnec.chords.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchItemDto {
    private long songId;
    private String performer;
    private String title;
    private String snippet;
}
