package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.web.dto.SongDto;

/**
 * @author zak <zak@robotnec.com>
 */
public interface DiffService {
    SongDto createDiff(SongDto songDto);

    Song undo(Long id);
}
