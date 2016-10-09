package com.robotnec.chords.persistence.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chord")
public class Chord {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String diagram;
}
