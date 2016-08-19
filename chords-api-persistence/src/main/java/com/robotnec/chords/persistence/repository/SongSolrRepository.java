package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface SongSolrRepository extends SolrCrudRepository<SongSolrDocument, Long> {

    @Highlight(prefix = "<[[", postfix = "]]>", fields = {"title", "lyrics", "performer"})
    @Query(value = "catch_all:?0")
    SolrResultPage<SongSolrDocument> findByAllFields(String term, Pageable pageable);
}
