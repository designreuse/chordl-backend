package com.robotnec.chords.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String lyrics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id")
    private Performer performer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "original")
    @OrderBy("timestamp desc")
    private List<History> histories;
}
