package com.robotnec.chords.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data
@Builder
@ToString
@SolrDocument
public class SongSolrDocument {

    @Id
    private long id;

    @Field
    private String title;

    @Field
    private String lyrics;

    @Field
    private String performer;
}
