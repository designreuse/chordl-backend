package com.robotnec.chords.web;

import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/search", produces = "application/json;charset=UTF-8")
public class SearchController {

    @Autowired
    SongService songService;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/{query}",method = RequestMethod.GET)
    public ResponseEntity<List<SongDto>> search(@PathVariable("query") final String query) {
        // TODO use search engine

        List<Song> songs = songService.getSongs();

        List<SongDto> results = songs.stream()
                .filter(song -> searchPredicate(query, song))
                .distinct()
                .sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()))
                .map(song -> mapper.map(song, SongDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    private boolean searchPredicate(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase())
                || song.getLyrics().toLowerCase().contains(query.toLowerCase());
    }
}
