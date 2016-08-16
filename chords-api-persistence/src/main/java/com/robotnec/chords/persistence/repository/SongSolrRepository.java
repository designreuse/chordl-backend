package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.SongSolrDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface SongSolrRepository extends SolrCrudRepository<SongSolrDocument, Long> {

    List<SongSolrDocument> findByText(String term);
}
