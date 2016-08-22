package com.robotnec.chords.web.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FeaturedDto {
    private List<SongDto> songs;
}
