package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.Diff;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.DiffRepository;
import com.robotnec.chords.service.DiffService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.SongDto;
import difflib.DiffUtils;
import difflib.Patch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@Service
public class DiffServiceImpl implements DiffService {

    @Autowired
    private DiffRepository diffRepository;

    @Autowired
    private SongService songService;

    @Override
    public SongDto createDiff(SongDto songDto) {
        Optional<Song> songOptional = songService.getSong(songDto.getId());

        Diff diff = new Diff();
        diff.setSongId(songDto.getId());
        diff.setDiff(getDiff(songDto, songOptional.get()));
        diffRepository.save(diff);
        return songDto;
    }

    @Transactional
    @Override
    public Song undo(Long id) {
        String diff = diffRepository.findBySongId(id).getDiff();

        Song song = songService.getSong(id).get();

        song.setLyrics(unPatch(song.getLyrics(), diff));

        songService.updateSong(song);

        return song;
    }

    private String unPatch(String text, String diff) {
        Patch patch = DiffUtils.parseUnifiedDiff(Arrays.asList(diff.split("\n")));
        List<String> list = (List<String>) DiffUtils.unpatch(Arrays.asList(text.split("\n")), patch);
        return list.stream().collect(Collectors.joining("\n"));
    }

    private String getDiff(SongDto songDto, Song song) {
        List<String> original = Arrays.asList(song.getLyrics().split("\n"));
        List<String> revised  = Arrays.asList(songDto.getLyrics().split("\n"));

        Patch patch = DiffUtils.diff(original, revised);
        return DiffUtils.generateUnifiedDiff(
                song.getTitle(),
                song.getTitle(),
                original,
                patch,
                song.getLyrics().length())
                .stream()
                .collect(Collectors.joining("\n"));
    }
}
