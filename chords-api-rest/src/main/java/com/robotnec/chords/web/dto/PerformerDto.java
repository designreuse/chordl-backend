package com.robotnec.chords.web.dto;

import lombok.*;

import java.util.Date;
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
public class PerformerDto {
    private long id;

    private String name;

    private Date createdDate;

    private Date updatedDate;
}
