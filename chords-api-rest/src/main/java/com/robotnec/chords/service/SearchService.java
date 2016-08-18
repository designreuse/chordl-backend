package com.robotnec.chords.service;

import com.robotnec.chords.web.dto.SearchItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<SearchItemDto> search(String term, Pageable pageable);
}
