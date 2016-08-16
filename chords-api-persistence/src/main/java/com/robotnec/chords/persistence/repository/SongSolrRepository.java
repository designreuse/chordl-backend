package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface SongSolrRepository extends SolrCrudRepository<SongSolrDocument, Long> {

    @Highlight(prefix = "<b>", postfix = "</b>")
    @Query(value = "catch_all:?0")
    List<SongSolrDocument> findByAllFields(String term);
}
