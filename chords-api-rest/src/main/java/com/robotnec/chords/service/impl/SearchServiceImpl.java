package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SearchService;
import com.robotnec.chords.web.dto.SearchItemDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.SolrResultPage;
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
    public Page<SearchItemDto> search(String term, Pageable pageable) {
        List<SearchItemDto> results = new ArrayList<>();

        SolrResultPage<SongSolrDocument> result = songSolrRepository.findByAllFields(term, pageable);
        List<HighlightEntry<SongSolrDocument>> highlightEntries = result.getHighlighted();

        highlightEntries.forEach(entry -> {
            SearchItemDto searchItemDto = mapper.map(entry.getEntity(), SearchItemDto.class);

            mapHighlightedFields(entry, searchItemDto);

            results.add(searchItemDto);
        });

        return new PageImpl<>(results, pageable, result.getTotalElements());
    }

    private void mapHighlightedFields(HighlightEntry<SongSolrDocument> entry, SearchItemDto searchItemDto) {
        List<HighlightEntry.Highlight> highlights = entry.getHighlights();
        highlights.forEach(highlight -> {
            String highlightFieldName = highlight.getField().getName();
            String snippet = highlight.getSnipplets().get(0);

            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.TITLE)) {
                searchItemDto.setTitle(snippet);
            }
            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.LYRICS)) {
                searchItemDto.setSnippet(snippet);
            }
            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.PERFORMER)) {
                searchItemDto.setPerformer(snippet);
            }
        });
    }

    private boolean equalsField(String highlightFieldName, SongSolrDocument.FieldName fieldName) {
        return highlightFieldName.equalsIgnoreCase(fieldName.name());
    }
}
