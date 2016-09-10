package com.robotnec.chords.persistence.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author zak <zak@robotnec.com>
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "song")
public class Song extends BaseEntity {

    private static final int TITLE = 0;
    private static final int BODY = 1;

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String lyrics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id")
    private Performer performer;
}
