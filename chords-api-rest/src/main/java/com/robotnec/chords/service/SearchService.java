package com.robotnec.chords.service;

import com.robotnec.chords.web.dto.SearchNodeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<SearchNodeDto> search(String term, Pageable pageable);
}
