package com.robotnec.chords.persistence.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "song")
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Song extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "lyrics")
    private String lyrics;
}
