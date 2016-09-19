package com.robotnec.chords.web;

import com.robotnec.chords.config.access.AdminAccess;
import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.service.HistoryService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.PrettyDiffDto;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/history", produces = "application/json;charset=UTF-8")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private SongService songService;

    @Autowired
    private Mapper mapper;

    @AdminAccess
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ResponseEntity<SongDto> applyFromHistory(@RequestParam("historyId") Long historyId) {
        return historyService.getHistory(historyId)
                .map(historyService::apply)
                .map(songService::updateSong)
                .map(song -> mapper.map(song, SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(historyId));
    }

    @RequestMapping(value = "/pretty", method = RequestMethod.GET)
    public ResponseEntity<PrettyDiffDto> getPrettyDiff(@RequestParam("historyId") Long historyId) {
        return historyService.getHistory(historyId)
                .map(historyService::prettyDiff)
                .map(diff -> mapper.map(diff, PrettyDiffDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(historyId));
    }
}
