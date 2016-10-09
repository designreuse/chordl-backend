package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Chord;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zak <zak@robotnec.com>
 */
public interface ChordRepository extends CrudRepository<Chord, Long> {
    Chord findByName(String name);
}
