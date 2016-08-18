package com.robotnec.chords.web;

import com.robotnec.chords.service.SearchService;
import com.robotnec.chords.web.dto.SearchItemDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<SearchItemDto>> search(@RequestParam final String query, final Pageable pageable) {
        Page<SearchItemDto> songs = searchService.search(query, pageable);
        return ResponseEntity.ok(songs);
    }
}
