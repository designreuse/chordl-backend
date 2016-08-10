package com.robotnec.chords.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "performer")
public class Performer extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "performer")
    private List<Song> songs;
}
