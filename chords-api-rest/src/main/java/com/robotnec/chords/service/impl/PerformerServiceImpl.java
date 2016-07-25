package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.Performer;
import com.robotnec.chords.persistence.repository.PerformerRepository;
import com.robotnec.chords.service.PerformerService;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@Service
public class PerformerServiceImpl implements PerformerService {

    @Autowired
    private PerformerRepository performerRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<Performer> getPerformers() {
        List<Performer> performers = new ArrayList<>();
        performerRepository.findAll().forEach(performers::add);
        return performers;
    }

    @Override
    public Performer createPerformer(Performer performer) {
        return performerRepository.save(performer);
    }
}
