package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.persistence.repository.SongSolrRepository;
import com.robotnec.chords.service.SearchService;
import com.robotnec.chords.web.dto.SearchNodeDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.apache.solr.client.solrj.util.ClientUtils;
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
    public Page<SearchNodeDto> search(String term, Pageable pageable) {
        String[] termTokens = term.split("\\s+");

        List<String> queryTokens = new ArrayList<>();
        for (String token : termTokens) {
            queryTokens.add(ClientUtils.escapeQueryChars(token) + "~1");
        }

        term = String.join(" ", queryTokens);

        List<SearchNodeDto> results = new ArrayList<>();

        SolrResultPage<SongSolrDocument> result = songSolrRepository.findByAllFields(term, pageable);
        List<HighlightEntry<SongSolrDocument>> highlightEntries = result.getHighlighted();

        highlightEntries.forEach(entry -> {
            SearchNodeDto searchNodeDto = mapper.map(entry.getEntity(), SearchNodeDto.class);

            mapHighlightedFields(entry, searchNodeDto);

            results.add(searchNodeDto);
        });

        return new PageImpl<>(results, pageable, result.getTotalElements());
    }

    private void mapHighlightedFields(HighlightEntry<SongSolrDocument> entry, SearchNodeDto searchNodeDto) {
        List<HighlightEntry.Highlight> highlights = entry.getHighlights();
        highlights.forEach(highlight -> {
            String highlightFieldName = highlight.getField().getName();
            String snippet = highlight.getSnipplets().get(0);

            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.TITLE)) {
                searchNodeDto.setTitle(snippet);
            }
            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.LYRICS)) {
                searchNodeDto.setSnippet(snippet);
            }
            if (equalsField(highlightFieldName, SongSolrDocument.FieldName.PERFORMER)) {
                searchNodeDto.setPerformer(snippet);
            }
        });
    }

    private boolean equalsField(String highlightFieldName, SongSolrDocument.FieldName fieldName) {
        return highlightFieldName.equalsIgnoreCase(fieldName.name());
    }
}
