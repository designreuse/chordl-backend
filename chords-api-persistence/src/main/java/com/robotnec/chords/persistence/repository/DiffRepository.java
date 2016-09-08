package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Diff;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zak <zak@robotnec.com>
 */
public interface DiffRepository extends JpaRepository<Diff, Long> {
    Diff findBySongId(Long songId);
}
