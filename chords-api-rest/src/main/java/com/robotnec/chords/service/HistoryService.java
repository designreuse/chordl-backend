package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.History;
import com.robotnec.chords.persistence.entity.Song;

import java.util.List;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
public interface HistoryService {
    Optional<History> getHistory(Long id);

    List<History> getHistoryForSongId(Long id);

    Song apply(History history);

    Song createHistory(Song newVersion);

    String prettyDiff(History history);
}
