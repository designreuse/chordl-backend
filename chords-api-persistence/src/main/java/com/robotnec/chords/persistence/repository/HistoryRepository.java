package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.History;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zak <zak@robotnec.com>
 */
public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {
}
