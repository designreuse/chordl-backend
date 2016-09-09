package com.robotnec.chords.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zak <zak@robotnec.com>
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diff")
public class Diff {

    @Id
    @GeneratedValue
    private Long id;
    private String diff;
    private Long songId;

    @DateTimeFormat(style = "M-")
    private Date timestamp;

    @PrePersist
    public void onCreate() {
        timestamp = new Date();
    }
}
