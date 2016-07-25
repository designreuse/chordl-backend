package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.Performer;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
public interface PerformerService {
    List<Performer> getPerformers();

    Performer createPerformer(Performer performer);
}
