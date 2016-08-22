package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.Performer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
public interface PerformerService {
    Optional<Performer> getPerformer(long id);

    Optional<Performer> getPerformerByName(String name);

    Performer createPerformer(Performer performer);

    Performer updatePerformer(Performer performer);

    Performer deletePerformer(long id);

    Page<Performer> getPerformers(Pageable pageable);

    List<Performer> getPerformers();
}
