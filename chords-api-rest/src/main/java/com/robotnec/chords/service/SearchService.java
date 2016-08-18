package com.robotnec.chords.service;

import com.robotnec.chords.web.dto.SearchItemDto;

import java.util.List;

public interface SearchService {
    List<SearchItemDto> search(String term);
}
