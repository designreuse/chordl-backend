package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.WrongArgumentException;
import com.robotnec.chords.persistence.entity.Chord;
import com.robotnec.chords.persistence.repository.ChordRepository;
import com.robotnec.chords.service.ChordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChordServiceImpl implements ChordService {

    @Autowired
    private ChordRepository chordRepository;

    @Override
    public Optional<Chord> findGuitarChordByName(String name) {
        return Optional.ofNullable(chordRepository.findByName(name));
    }

    @Override
    public Chord createChord(Chord chord) {
        return chordRepository.save(chord);
    }

    @Override
    public Chord updateChord(Chord chord) {
        return createChord(chord);
    }

    @Override
    public Chord deleteChord(long id) {
        return Optional.ofNullable(chordRepository.findOne(id))
                .map(this::deleteChord)
                .orElseThrow(() -> new WrongArgumentException(String.format("Chord with id '%s' not found", id)));
    }

    @Override
    public List<Chord> getChords() {
        List<Chord> chords = new ArrayList<>();
        chordRepository.findAll().forEach(chords::add);
        return chords;
    }

    private Chord deleteChord(Chord song) {
        chordRepository.delete(song.getId());
        return song;
    }
}
