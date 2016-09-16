package com.robotnec.chords.web;

import com.robotnec.chords.config.access.AdminAccess;
import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.service.HistoryService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/songs", produces = "application/json;charset=UTF-8")
public class SongsController {

    @Autowired
    private SongService songService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private Mapper mapper;

    @AdminAccess
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SongDto>> getSongs() {
        return ResponseEntity.ok(mapper.mapAsList(songService.getSongs(), SongDto.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SongDto> getSong(@PathVariable("id") Long id) {
        return Optional.of(id)
                .flatMap(v -> songService.getSong(v))
                .map(v -> mapper.map(v, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SongDto> createSong(@Valid @RequestBody SongDto songDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(songDto)
                .map(v -> mapper.map(v, Song.class))
                .map(songService::createSong)
                .map(v -> mapper.map(v, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SongDto> updateSong(@Valid @RequestBody SongDto songDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(songDto)
                .map(v -> mapper.map(v, Song.class))
                .map(historyService::createHistory)
                .map(songService::updateSong)
                .map(v -> mapper.map(v, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @AdminAccess
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public ResponseEntity<List<SongDto>> createSongs(@Valid @RequestBody List<SongDto> songDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(songDto)
                .map(v -> mapper.mapAsList(v, Song.class))
                .map(songService::createSongs)
                .map(v -> mapper.mapAsList(v, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<SongDto> deleteSong(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(songService::deleteSong)
                .map(v -> mapper.map(v, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongArgumentException(String.format("Song with id '%s' not found", id)));
    }
}