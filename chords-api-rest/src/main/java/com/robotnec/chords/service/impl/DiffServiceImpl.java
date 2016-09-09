package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.persistence.entity.Diff;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.DiffRepository;
import com.robotnec.chords.service.DiffService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.util.ListUtil;
import difflib.DiffUtils;
import difflib.Patch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@Slf4j
@Service
public class DiffServiceImpl implements DiffService {

    @Autowired
    private DiffRepository diffRepository;

    @Autowired
    private SongService songService;

    @Transactional
    @Override
    public Song createDiff(Song newVersion) {
        Long songId = newVersion.getId();
        return songService.getSong(songId)
                .map(oldVersion -> createDiff(newVersion, oldVersion))
                .map(diff -> Diff.builder().diff(diff).songId(newVersion.getId()).build())
                .map(diffRepository::save)
                .map(done -> newVersion)
                .orElseThrow(() -> new ResourceNotFoundException(songId));
    }

    @Override
    public List<Diff> getDiffs(Long id) {
        return diffRepository.findBySongId(id, byTimestamp());
    }

    @Transactional
    @Override
    public Song undo(Long diffId, Song song) {
        List<Diff> diffsAll = diffRepository.findBySongId(song.getId(), byTimestamp());

        if (containsDiff(diffsAll, diffId)) {

            List<Diff> diffsToApply =
                    ListUtil.greedyTakeWhile(diffsAll, diff -> !diff.getId().equals(diffId));

            diffsToApply.forEach(diff -> song.setLyrics(unPatchText(song.getLyrics(), diff.getDiff())));

            diffRepository.delete(diffsToApply);

            return song;
        } else {
            throw new ResourceNotFoundException("diff", diffId);
        }
    }

    private boolean containsDiff(List<Diff> diffsAll, Long diffId) {
        return diffsAll.stream().anyMatch(diff -> diff.getId().equals(diffId));
    }

    private String unPatchText(String text, String diff) {
        Patch patch = DiffUtils.parseUnifiedDiff(Arrays.asList(diff.split("\n")));
        List<String> list = (List<String>) DiffUtils.unpatch(Arrays.asList(text.split("\n")), patch);
        return list.stream().collect(Collectors.joining("\n"));
    }

    private String createDiff(Song newVersion, Song oldVersion) {
        List<String> original = Arrays.asList(oldVersion.getLyrics().split("\n"));
        List<String> revised  = Arrays.asList(newVersion.getLyrics().split("\n"));

        Patch patch = DiffUtils.diff(original, revised);
        return DiffUtils.generateUnifiedDiff(
                String.format("a %s", oldVersion.getTitle()),
                String.format("b %s", oldVersion.getTitle()),
                original,
                patch,
                oldVersion.getLyrics().length())
                .stream()
                .collect(Collectors.joining("\n"));
    }

    private Sort byTimestamp() {
        return new Sort(Sort.Direction.DESC, "timestamp");
    }
}
