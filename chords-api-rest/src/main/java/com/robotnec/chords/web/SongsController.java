package com.robotnec.chords.web;

import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/songs", produces = "application/json;charset=UTF-8")
public class SongsController {

    @Autowired
    SongRepository songRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Song>> getSongs() {
        return new ResponseEntity<>(songRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Song> getSongs(@PathVariable("id") Long id) {
        return new ResponseEntity<>(songRepository.findOne(id), HttpStatus.OK);
    }

}