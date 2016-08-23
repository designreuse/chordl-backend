package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.PerformerRepository;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
                    .performerId(performer.getId())
                    .build());

            return savedSong;
        } else {
            throw new WrongArgumentException(String.format("Performer '%s' not found", song.getPerformer()));
        }
    }

    @Transactional
    @Override
    public List<Song> createSongs(List<Song> songs) {
        Iterable<Song> createdSongs = songs
                .stream()
                .map(song -> {
                    long performerId = song.getPerformer().getId();
                    Optional.ofNullable(performerRepository.findOne(performerId))
                            .map(performer -> {
                                song.setPerformer(performer);
                                return performer;
                            })
                            .orElseThrow(() -> new WrongArgumentException(String.format("Performer '%s' not found", performerId)));
                    return song;
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), savedSongs -> songRepository.save(savedSongs)));

        StreamSupport.stream(createdSongs.spliterator(), false)
                .map(song -> SongSolrDocument.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .lyrics(song.getLyrics())
                        .performer(song.getPerformer().getName())
                        .performerId(song.getPerformer().getId())
                        .build())
                .collect(Collectors.collectingAndThen(Collectors.toList(), solrDocuments -> songSolrRepository.save(solrDocuments)));

        return StreamSupport.stream(createdSongs.spliterator(), false).collect(Collectors.toList());
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

    @Override
    public List<Song> getRecentlyUpdatedSongs(int amount) {
        return songRepository.findTop20ByOrderByUpdatedDateDesc();
    }

    private Song deleteSong(Song song) {
        songRepository.delete(song.getId());
        return song;
    }
}
