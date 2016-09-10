package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.Diff;
import com.robotnec.chords.persistence.entity.Song;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
public interface DiffService {
    Song createDiff(Song original, Song revised);

    List<Diff> getDiffs(Long id);

    Song undo(Long diffId, Song versionControllable);
}
