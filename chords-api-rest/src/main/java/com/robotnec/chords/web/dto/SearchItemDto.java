package com.robotnec.chords.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchItemDto {
    private Long songId;
    private Long performerId;
    private String performer;
    private String title;
    private String snippet;
}
