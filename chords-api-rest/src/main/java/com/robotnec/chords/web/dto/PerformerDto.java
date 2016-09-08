package com.robotnec.chords.web.dto;

import com.robotnec.chords.web.serializer.Trim;
import lombok.*;

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
public class PerformerDto {
    private long id;

    @Trim
    @Size(min = 2, max = 60)
    @Pattern(regexp = "^[^\\\\/<>\\^`{}]+$")
    private String name;

    private Date createdDate;

    private Date updatedDate;

    private int songCount;
}
