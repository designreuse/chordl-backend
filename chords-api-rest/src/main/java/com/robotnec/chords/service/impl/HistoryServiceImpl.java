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
    public List<History> getHistoryForSongId(Long id) {
        return historyRepository.findByRelativeEntityId(id, byTimestampDesc());
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
        Long songId = history.getRelativeEntityId();
        return Optional.ofNullable(songRepository.findOne(songId))
                .map(song -> createDiff(song, history))
                .map(diffMatchPatch::diffPrettyHtml)
                .orElseThrow(() -> new ResourceNotFoundException("song", songId));
    }

    private LinkedList<DiffMatchPatch.Diff> createDiff(Song song, History history) {
        LinkedList<DiffMatchPatch.Diff> diff =
                diffMatchPatch.diffMain(song.getTitle(), history.getTextTitle());
        LinkedList<DiffMatchPatch.Diff> bodyDiff =
                diffMatchPatch.diffMain(song.getLyrics(), history.getTextBody());
        diff.addAll(bodyDiff);
        return diff;
    }

    @Override
    public Song apply(History history) {
        Long songId = history.getRelativeEntityId();
        Song song = Optional.ofNullable(songRepository.findOne(songId))
                .map(this::copy)
                .orElseThrow(() -> new ResourceNotFoundException("song", songId));

        song.setTitle(history.getTextTitle());
        song.setLyrics(history.getTextBody());
        return song;
    }

    private History buildHistory(Song song) {
        return History.builder()
                .relativeEntityId(song.getId())
                .textTitle(song.getTitle())
                .textBody(song.getLyrics())
                .build();
    }

    private Song copy(Song song) {
        return Song.builder()
                .id(song.getId())
                .performer(song.getPerformer())
                .build();
    }

    private Sort byTimestampDesc() {
        return new Sort(Sort.Direction.DESC, "timestamp");
    }
}
