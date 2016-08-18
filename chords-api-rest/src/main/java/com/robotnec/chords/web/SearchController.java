package com.robotnec.chords.web;

import com.robotnec.chords.service.SearchService;
import com.robotnec.chords.web.dto.SearchItemDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/search", produces = "application/json;charset=UTF-8")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/{query}",method = RequestMethod.GET)
    public ResponseEntity<List<SearchItemDto>> search(@PathVariable("query") final String query) {
        List<SearchItemDto> songs = searchService.search(query);
        return ResponseEntity.ok(songs);
    }
}
