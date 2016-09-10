package com.robotnec.chords.service.impl;

import com.robotnec.chords.exception.ResourceNotFoundException;
import com.robotnec.chords.persistence.entity.Diff;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.DiffRepository;
import com.robotnec.chords.service.DiffService;
import com.robotnec.chords.util.DiffUtil;
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
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@Slf4j
@Service
public class DiffServiceImpl implements DiffService {

    @Autowired
    private DiffRepository diffRepository;

    @Transactional
    @Override
    public Song createDiff(Song original, Song revised) {
        String diffText = createDiffText(revised, original);
        Diff diff = Diff.builder()
                .diff(diffText)
                .relativeEntityId(revised.getId())
                .build();

        if (!diff.getDiff().isEmpty()) {
            diffRepository.save(diff);
        }
        return revised;
    }

    @Override
    public List<Diff> getDiffs(Long id) {
        return diffRepository.findByRelativeEntityId(id, byTimestampDesc());
    }

    @Transactional(readOnly = true)
    @Override
    public Song undo(Long diffId, Song original) {
        List<Diff> diffsAll = diffRepository.findByRelativeEntityId(original.getId(), byTimestampDesc());

        if (containsDiff(diffsAll, diffId)) {
            List<Diff> diffsToApply =
                    ListUtil.greedyTakeWhile(diffsAll, diff -> !diff.getId().equals(diffId));

            return diffsToApply.stream()
                    .reduce(original, this::unPatch, (song, song2) -> song2);
        } else {
            throw new ResourceNotFoundException("diff", diffId);
        }
    }

    private boolean containsDiff(List<Diff> diffsAll, Long diffId) {
        return diffsAll.stream().anyMatch(diff -> diff.getId().equals(diffId));
    }

    private String createDiffText(Song revised, Song original) {
        List<String> originalLines = DiffUtil.split(original);
        List<String> revisedLines = DiffUtil.split(revised);
        Patch patch = DiffUtils.diff(originalLines, revisedLines);
        return DiffUtils.generateUnifiedDiff(
                "original",
                "revised",
                originalLines,
                patch,
                0)
                .stream()
                .collect(Collectors.joining("\n"));
    }

    @SuppressWarnings("unchecked")
    private Song unPatch(Song song, Diff diff) {
        List<String> lines = DiffUtil.split(song);

        Patch patch = DiffUtils.parseUnifiedDiff(Arrays.asList(diff.getDiff().split("\n")));
        List<?> list = DiffUtils.unpatch(lines, patch);

        Song patched = DiffUtil.join((List<String>) list);
        patched.setId(song.getId());
        patched.setPerformer(song.getPerformer());
        return patched;
    }

    private Sort byTimestampDesc() {
        return new Sort(Sort.Direction.DESC, "timestamp");
    }

}
