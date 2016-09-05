package com.robotnec.chords.web.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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

    @Size(min = 2, max = 60)
    private String title;

    @Size(min = 2)
    private String lyrics;

    private Date createdDate;

    private Date updatedDate;

    @Min(0)
    private long performerId;

    private String performerName;
}
