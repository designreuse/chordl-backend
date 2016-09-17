package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.History;
import com.robotnec.chords.persistence.entity.Song;

import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
public interface HistoryService {
    Optional<History> getHistory(Long id);

    Song apply(History history);

    String prettyDiff(History history);
}
