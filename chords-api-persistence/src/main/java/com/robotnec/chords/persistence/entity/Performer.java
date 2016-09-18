package com.robotnec.chords.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author zak <zak@robotnec.com>
 */
@EqualsAndHashCode(callSuper = true, exclude = "songs")
@Data
@Entity
@Table(name = "performer")
public class Performer extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "performer")
    private Set<Song> songs;
}
