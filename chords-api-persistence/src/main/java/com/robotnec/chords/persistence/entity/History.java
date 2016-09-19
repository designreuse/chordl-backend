package com.robotnec.chords.persistence.entity;

import com.robotnec.chords.persistence.entity.user.ChordsUser;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zak <zak@robotnec.com>
 */
@EqualsAndHashCode(exclude = {"original", "createdBy"})
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue
    private Long id;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song original;

    @DateTimeFormat(style = "M-")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private ChordsUser createdBy;

    @PrePersist
    public void onCreate() {
        timestamp = new Date();
    }

    public static History from(Song song) {
        return History.builder()
                .original(song)
                .body(song.getLyrics())
                .build();
    }
}
