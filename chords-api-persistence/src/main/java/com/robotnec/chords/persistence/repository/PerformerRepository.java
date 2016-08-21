package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Performer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PerformerRepository extends PagingAndSortingRepository<Performer, Long> {
}
