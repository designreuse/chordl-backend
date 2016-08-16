package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SongSolrRepository songSolrRepository;

    @Override
    public List<SongSolrDocument> search(String term) {
        return songSolrRepository.findByAllFields(term);
    }
}
