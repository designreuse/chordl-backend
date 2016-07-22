package com.robotnec.chords.web;

import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song")
public class SongsController {

    @Autowired
    SongRepository songRepository;

    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    public String getSongs() {
        songRepository.save(new Song(0, "Hello", "askldjalskjd"));
        return songRepository.findAll().toString();
    }

}