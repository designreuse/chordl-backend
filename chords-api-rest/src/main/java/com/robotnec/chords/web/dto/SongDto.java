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
public class SongDto {
    private long id;

    private String title;

    private String lyrics;

    private Date createdDate;

    private Date updatedDate;

    private long performerId;

    private String performerName;
}
