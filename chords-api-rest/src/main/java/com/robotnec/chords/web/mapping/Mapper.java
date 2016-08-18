package com.robotnec.chords.web.mapping;

import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.entity.SongSolrDocument;
import com.robotnec.chords.web.dto.PerformerDto;
import com.robotnec.chords.web.dto.SearchItemDto;
import com.robotnec.chords.web.dto.SongDto;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@Component
public class Mapper {

    private MapperFacade mapperFacade;

    @PostConstruct
    private void postConstruct() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Song.class, SongDto.class)
                .field("performer.id", "performerId")
                .field("performer.name", "performerName")
                .byDefault()
                .register();
        mapperFactory.classMap(Song.class, Song.class)
                .exclude("id")
                .mapNulls(false)
                .byDefault()
                .register();
        mapperFactory.classMap(Performer.class, PerformerDto.class)
                .byDefault()
                .register();
        mapperFactory.classMap(SongSolrDocument.class, SearchItemDto.class)
                .field("id", "songId")
                .byDefault()
                .register();
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public <T> T map(Object source, Class<T> destination) {
        return mapperFacade.map(source, destination);
    }

    public void map(Object source, Object destination) {
        mapperFacade.map(source, destination);
    }

    public <S, D> List<D> mapAsList(Iterable<S> iterable, Class<D> destination) {
        return mapperFacade.mapAsList(iterable, destination);
    }
}
