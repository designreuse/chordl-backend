package com.robotnec.chords.web;

import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.service.PerformerService;
import com.robotnec.chords.web.dto.IndexDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/index", produces = "application/json;charset=UTF-8")
public class IndexController {

    @Autowired
    PerformerService performerService;

    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<IndexDto>> getIndex() {
        List<Performer> performers = performerService.getPerformers();

        List<IndexDto> symbols = performers.stream()
                .map(performer -> performer.getName().substring(0, 1).toUpperCase())
                .distinct()
                .sorted()
                .map(IndexDto::new)
                .collect(Collectors.toList());

        symbols.add(0, new IndexDto("#"));

        return ResponseEntity.ok(symbols);
    }
}
