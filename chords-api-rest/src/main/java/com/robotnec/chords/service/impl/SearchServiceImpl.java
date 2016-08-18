package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SearchService;
import com.robotnec.chords.web.dto.SearchItemDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SongSolrRepository songSolrRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<SearchItemDto> search(String term) {
        List<SearchItemDto> results = new ArrayList<>();

        Pageable pageable = new PageRequest(0, 10);
        HighlightPage<SongSolrDocument> result = songSolrRepository.findByAllFields(term, pageable);
        List<HighlightEntry<SongSolrDocument>> highlightEntries = result.getHighlighted();

        highlightEntries.forEach(entry -> {
            SearchItemDto searchItemDto = mapper.map(entry.getEntity(), SearchItemDto.class);

            mapHighlightedFields(entry, searchItemDto);

            results.add(searchItemDto);
        });

        return results;
    }

    private void mapHighlightedFields(HighlightEntry<SongSolrDocument> entry, SearchItemDto searchItemDto) {
        List<HighlightEntry.Highlight> highlights = entry.getHighlights();
        highlights.forEach(highlight -> {
            String highlightFieldName = highlight.getField().getName();

            if (highlightFieldName.equalsIgnoreCase(SongSolrDocument.FieldName.TITLE.name())) {
                searchItemDto.setTitle(highlight.getSnipplets().get(0));
            }
            if (highlightFieldName.equalsIgnoreCase(SongSolrDocument.FieldName.LYRICS.name())) {
                searchItemDto.setSnippet(highlight.getSnipplets().get(0));
            }
            if (highlightFieldName.equalsIgnoreCase(SongSolrDocument.FieldName.PERFORMER.name())) {
                searchItemDto.setPerformer(highlight.getSnipplets().get(0));
            }
        });
    }
}
