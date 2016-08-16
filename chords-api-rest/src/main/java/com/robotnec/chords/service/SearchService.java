package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.SongSolrDocument;

import java.util.List;

public interface SearchService {
    List<SongSolrDocument> search(String term);
}
