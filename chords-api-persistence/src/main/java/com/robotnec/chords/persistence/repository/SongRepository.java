package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> findTop20ByOrderByUpdatedDateDesc();
}
