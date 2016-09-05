package com.robotnec.chords.web;

import com.robotnec.chords.config.access.AdminAccess;
import com.robotnec.chords.exception.InvalidRequestException;
import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.service.PerformerService;
import com.robotnec.chords.web.dto.PerformerDto;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/performers", produces = "application/json;charset=UTF-8")
public class PerformersController {

    @Autowired
    private PerformerService performerService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<PerformerDto>> getPerformers(Pageable pageable) {
        return Optional.of(performerService.getPerformers(pageable))
                .map(v -> mapper.mapAsPage(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllPerformerNames() {
        List<String> result = performerService.getPerformers().stream()
                .map(Performer::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PerformerDto> createPerformer(@Valid @RequestBody PerformerDto performerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

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
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // used in scripts
    @AdminAccess
    @Deprecated
    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<PerformerDto> getPerformerByName(@PathVariable("name") final String name) {
        return Optional.of(name)
                .flatMap(v -> performerService.getPerformerByName(v))
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    @RequestMapping(value = "v2/search", method = RequestMethod.GET)
    public ResponseEntity<PerformerDto> getPerformerByNameV2(@RequestParam final String name) {
        return Optional.of(name)
                .flatMap(v -> performerService.getPerformerByName(v))
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    @RequestMapping(value = "/{id}/songs", method = RequestMethod.GET)
    public ResponseEntity<List<SongDto>> getPerformerSongs(@PathVariable("id") final Long id) {
        return Optional.of(id)
                .flatMap(v -> performerService.getPerformer(v))
                .map(v -> mapper.mapAsList(v.getSongs(), SongDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @AdminAccess
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PerformerDto> updatePerformer(@PathVariable("id") final Long id,
                                                        @Valid @RequestBody final PerformerDto songDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return Optional.of(songDto)
                .map(v -> mapper.map(v, Performer.class))
                .map(v -> setId(v, id))
                .map(performerService::updatePerformer)
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(IllegalStateException::new);
    }

    @AdminAccess
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PerformerDto> deletePerformer(@PathVariable("id") final Long id) {
        return Optional.of(id)
                .map(performerService::deletePerformer)
                .map(v -> mapper.map(v, PerformerDto.class))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongArgumentException(String.format("Performer with id '%s' not found", id)));
    }

    private Performer setId(Performer performer, Long id) {
        performer.setId(id);
        return performer;
    }
}