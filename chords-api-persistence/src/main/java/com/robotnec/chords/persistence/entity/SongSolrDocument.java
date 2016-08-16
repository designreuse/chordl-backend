package com.robotnec.chords.persistence.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data
@SolrDocument()
public class SongSolrDocument {

    private String id;

    private String title;

    private String lyrics;

    @Field("catch_all")
    private String text;
}
