package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.PerformerRepository;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongSolrRepository songSolrRepository;

    @Autowired
    private PerformerRepository performerRepository;

    @Override
    public Optional<Song> getSong(long id) {
        return Optional.ofNullable(songRepository.findOne(id));
    }

    @Transactional
    @Override
    public Song createSong(Song song) {
        Performer performer = song.getPerformer();

        performer = performerRepository.findOne(performer.getId());

        if (performer != null) {
            Song savedSong = songRepository.save(song);

            songSolrRepository.save(SongSolrDocument.builder()
                    .id(savedSong.getId())
                    .title(savedSong.getTitle())
                    .lyrics(savedSong.getLyrics())
                    .performer(performer.getName())
                    .build());

            return savedSong;
        } else {
            throw new WrongArgumentException(String.format("Performer '%s' not found", performer));
        }
    }

    @Override
    public Song updateSong(Song song) {
        return createSong(song);
    }

    @Transactional
    @Override
    public Song deleteSong(long id) {
        Song deletedSong = getSong(id)
                .map(this::deleteSong)
                .orElseThrow(() -> new WrongArgumentException(String.format("Song with id '%s' not found", id)));

        songSolrRepository.delete(deletedSong.getId());

        return deletedSong;
    }

    @Override
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add);
        return songs;
    }

    private Song deleteSong(Song song) {
        songRepository.delete(song.getId());
        return song;
    }
}
