package com.robotnec.chords.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author zak <zak@robotnec.com>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "song")
public class Song extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String lyrics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id")
    private Performer performer;
}
