package com.robotnec.chords.web;

import com.robotnec.chords.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/search", produces = "application/json;charset=UTF-8")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam final String query, final Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().body("Query is empty");
        }
        return ResponseEntity.ok(searchService.search(query, pageable));
    }
}
