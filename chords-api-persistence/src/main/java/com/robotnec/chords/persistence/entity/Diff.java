package com.robotnec.chords.persistence.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zak <zak@robotnec.com>
 */
@Data
@Entity
@Table(name = "diff")
public class Diff {

    @Id
    @GeneratedValue
    private Long id;
    private String diff;
    private Long songId;
}
