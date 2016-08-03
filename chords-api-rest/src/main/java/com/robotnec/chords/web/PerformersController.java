package com.robotnec.chords.web;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.service.PerformerService;
import com.robotnec.chords.web.dto.PerformerDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/performers", produces = "application/json;charset=UTF-8")
public class PerformersController {

    @Autowired
    private PerformerService performerService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PerformerDto>> getPerformers() {
        return ResponseEntity.ok(mapper.mapAsList(performerService.getPerformers(), PerformerDto.class));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PerformerDto> createPerformer(@RequestBody PerformerDto performerDto) {
        return Optional.of(performerDto)
                .map(v -> mapper.map(v, Performer.class))
                .map(performerService::createPerformer)
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PerformerDto> getPerformer(@PathVariable("id") final Long id) {
        return Optional.of(id)
                .flatMap(v -> performerService.getPerformer(v))
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongArgumentException(String.format("Performer with id '%s' not found", id)));
    }
}