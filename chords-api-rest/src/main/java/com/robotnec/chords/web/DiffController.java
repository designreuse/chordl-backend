package com.robotnec.chords.web;

import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.service.DiffService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.DiffDto;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/diff", produces = "application/json;charset=UTF-8")
public class DiffController {

    @Autowired
    private DiffService diffService;

    @Autowired
    private SongService songService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/undo", method = RequestMethod.GET)
    public ResponseEntity<SongDto> undo(@RequestParam("diffId") final Long diffId,
                                        @RequestParam("songId") final Long songId) {
        return songService.getSong(songId)
                .map(song -> diffService.undo(diffId, song))
                .map(songService::updateSong)
                .map(song -> mapper.map(song, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("song", songId));
    }

    @RequestMapping(value = "/{songId}", method = RequestMethod.GET)
    public ResponseEntity<List<DiffDto>> getDiffs(@PathVariable("songId") final Long songId) {
        return ResponseEntity.ok(mapper.mapAsList(diffService.getDiffs(songId), DiffDto.class));
    }
}
