package com.robotnec.chords.web;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/songs", produces = "application/json;charset=UTF-8")
public class SongsController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Song>> getSongs() {
        return ResponseEntity.ok(songRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {
        Song song = songRepository.findOne(id);
        if (song != null) {
            return ResponseEntity.ok(mapper.map(song, SongDto.class));
        }
        throw new WrongArgumentException(String.format("Song with id '%s' not found", id));
    }

}