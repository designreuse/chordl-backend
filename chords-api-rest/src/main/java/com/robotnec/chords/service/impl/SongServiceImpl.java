package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private Mapper mapper;

    @Override
    public Optional<Song> getSong(long id) {
        return Optional.ofNullable(songRepository.findOne(id));
    }

    @Override
    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song updateSong(Song song) {
        return getSong(song.getId())
                .map(v -> mapSong(song, v))
                .map(v -> songRepository.save(v))
                .orElseThrow(() -> new WrongArgumentException(String.format("Song with id '%s' not found", song.getId())));
    }

    @Override
    public Song deleteSong(long id) {
        return getSong(id)
                .map(this::deleteSong)
                .orElseThrow(() -> new WrongArgumentException(String.format("Song with id '%s' not found", id)));
    }

    @Override
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add);
        return songs;
    }

    private Song mapSong(Song source, Song destination) {
        mapper.map(source, destination);
        return destination;
    }

    private Song deleteSong(Song song) {
        songRepository.delete(song.getId());
        return song;
    }
}
