package com.robotnec.chords.web.dto;

import com.robotnec.chords.web.serializer.Trim;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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

    @Trim
    @Size(min = 2, max = 60)
    @Pattern(regexp = "^[^\\\\/<>\\^`{}]+$")
    private String title;

    @Trim
    @Size(min = 2)
    @Pattern(regexp = "^[^\\\\/<>\\^`{}]+$")
    private String lyrics;

    private Date createdDate;

    private Date updatedDate;

    @Min(0)
    private long performerId;

    @Trim
    @Pattern(regexp = "^[^\\\\/<>\\^`{}]+$")
    private String performerName;
}
