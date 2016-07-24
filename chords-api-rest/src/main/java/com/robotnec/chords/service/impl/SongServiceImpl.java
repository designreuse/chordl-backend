package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.service.SongService;
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
        return songRepository.save(song);
    }

    @Override
    public Song deleteSong(long id) {
        Song song = getSong(id)
                .orElseThrow(() -> new WrongArgumentException(String.format("Song with id '%s' not found", id)));

        songRepository.delete(id);

        return song;
    }

    @Override
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add);
        return songs;
    }
}
