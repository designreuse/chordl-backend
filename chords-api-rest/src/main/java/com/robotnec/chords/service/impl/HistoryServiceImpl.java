package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.persistence.entity.History;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.HistoryRepository;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.service.HistoryService;
import com.robotnec.chords.util.DiffMatchPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private SongRepository songRepository;

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    @Override
    public Optional<History> getHistory(Long id) {
        return Optional.ofNullable(historyRepository.findOne(id));
    }

    @Override
    public List<History> getHistoriesBySongId(Long id) {
        return null;//historyRepository.findBySongId(id, byTimestampDesc());
    }

    @Override
    public Song createHistory(Song newVersion) {
        Long songId = newVersion.getId();
        Optional.ofNullable(songRepository.findOne(songId))
                .map(this::buildHistory)
                .map(historyRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("song", songId));
        return newVersion;
    }

    @Override
    public String prettyDiff(History history) {
        String originalLyrics = history.getOriginal().getLyrics();
        String historyLyrics = history.getBody();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diffMain(originalLyrics, historyLyrics);
        return diffMatchPatch.diffPrettyHtml(diffs);
    }

    @Override
    public Song apply(History history) {
        Song original = history.getOriginal();
        original.setLyrics(history.getBody());
        return original;
    }

    private History buildHistory(Song song) {
        return History.builder()
                .original(song)
                .body(song.getLyrics())
                .build();
    }

    private Sort byTimestampDesc() {
        return new Sort(Sort.Direction.DESC, "timestamp");
    }
}
