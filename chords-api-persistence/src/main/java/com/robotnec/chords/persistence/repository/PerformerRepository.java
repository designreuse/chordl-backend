package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.persistence.entity.Song;
import org.springframework.data.repository.CrudRepository;

public interface PerformerRepository extends CrudRepository<Performer, Long> {
    Performer getByName(String name);
}
