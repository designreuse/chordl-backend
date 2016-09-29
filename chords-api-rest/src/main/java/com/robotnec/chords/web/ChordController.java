package com.robotnec.chords.web;

import com.robotnec.chords.config.access.AdminAccess;
import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Chord;
import com.robotnec.chords.service.ChordService;
import com.robotnec.chords.web.dto.GuitarChordDto;
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
@RequestMapping(value = "/chord", produces = "application/json;charset=UTF-8")
public class ChordController {

    @Autowired
    private ChordService chordService;

    @Autowired
    private Mapper mapper;

    @AdminAccess
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GuitarChordDto>> getChords() {
        return ResponseEntity.ok(mapper.mapAsList(chordService.getChords(), GuitarChordDto.class));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GuitarChordDto> createChord(@Valid @RequestBody GuitarChordDto chordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(chordDto)
                .map(v -> mapper.map(v, Chord.class))
                .map(chordService::createChord)
                .map(v -> mapper.map(v, GuitarChordDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @AdminAccess
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<GuitarChordDto> updateChord(@Valid @RequestBody GuitarChordDto songDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(songDto)
                .map(v -> mapper.map(v, Chord.class))
                .map(chordService::updateChord)
                .map(v -> mapper.map(v, GuitarChordDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @AdminAccess
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GuitarChordDto> deleteChord(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(chordService::deleteChord)
                .map(v -> mapper.map(v, GuitarChordDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongArgumentException(String.format("Chord with id '%s' not found", id)));
    }
}
