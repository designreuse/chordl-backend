package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.History;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.HistoryRepository;
import com.robotnec.chords.service.HistoryService;
import com.robotnec.chords.util.DiffMatchPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    @Override
    public Optional<History> getHistory(Long id) {
        return Optional.ofNullable(historyRepository.findOne(id));
    }

    @Override
    public String prettyDiff(History history) {
        String originalLyrics = history.getOriginal().getLyrics();
        String historyLyrics = history.getBody();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diffMain(historyLyrics, originalLyrics);
        return diffMatchPatch.diffPrettyHtml(diffs);
    }

    @Override
    public Song apply(History history) {
        Song original = history.getOriginal();
        Song revised = Song.builder()
                .id(original.getId())
                .title(original.getTitle())
                .lyrics(original.getLyrics())
                .performer(original.getPerformer())
                .histories(original.getHistories())
                .build();
        revised.setLyrics(history.getBody());
        return revised;
    }
}
