package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.Diff;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
public interface DiffRepository extends PagingAndSortingRepository<Diff, Long> {
    List<Diff> findBySongId(Long songId, Sort sort);
}
