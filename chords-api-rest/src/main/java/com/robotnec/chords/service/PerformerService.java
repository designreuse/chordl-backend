package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.Performer;

import java.util.List;
import java.util.Optional;

/**
 * @author zak <zak@robotnec.com>
 */
public interface PerformerService {
    Optional<Performer> getPerformer(long id);

    Performer createPerformer(Performer performer);

    Performer updatePerformer(Performer performer);

    Performer deletePerformer(long id);

    List<Performer> getPerformers();
}
