package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.History;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {
    List<History> findByRelativeEntityId(Long relativeEntityId, Sort sort);
}
