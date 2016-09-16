package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.Diff;
import com.robotnec.chords.persistence.entity.Song;
import com.robotnec.chords.persistence.repository.DiffRepository;
import com.robotnec.chords.persistence.repository.SongRepository;
import com.robotnec.chords.service.DiffService;
import com.robotnec.chords.service.SongService;
import com.robotnec.chords.util.DiffMatchPatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@Slf4j
@Service
public class DiffServiceImpl implements DiffService {

    @Autowired
    private DiffRepository diffRepository;

    @Autowired
    private SongRepository songRepository;

    @Transactional
    @Override
    public Song createDiff(Song original, Song revised) {
        if (original.equals(revised)) {
            return revised;
        }

        String diffText = createDiffText(original);
        Diff diff = Diff.builder()
                .diff(diffText)
                .relativeEntityId(original.getId())
                .build();

        if (!diff.getDiff().isEmpty()) {
            diffRepository.save(diff);
        }
        return revised;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Diff> getDiffs(Long id) {
        Song song = songRepository.findOne(id);
        List<Diff> diffs = diffRepository.findByRelativeEntityId(id, byTimestampDesc());

        diffs.forEach(diff -> diff.setDiff(getDiffPretty(diff.getId(), song)));

        return diffs;
    }

    @Override
    public Song undo(Long diffId, Song original) {
        Diff diff = diffRepository.findOne(diffId);
        return applyDiff(original, diff);
    }

    @Override
    public String getDiffPretty(Long diffId, Song song) {
        Diff diff = diffRepository.findOne(diffId);
        DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diff_main(diff.getDiff(), createDiffText(song));
        return diffMatchPatch.diff_prettyHtml(diffs);
    }

    private String createDiffText(Song revised) {
        return String.format("%s^%s", revised.getTitle(), revised.getLyrics());
    }

    private Song applyDiff(Song song, Diff diff) {
        Song result = new Song();

        String[] nodes = diff.getDiff().split("\\^");

        String title = nodes[0];
        String body = StringUtils.join(Arrays.copyOfRange(nodes, 1, nodes.length));

        result.setTitle(title);
        result.setLyrics(body);
        result.setId(song.getId());
        result.setPerformer(song.getPerformer());
        return result;
    }

    private Sort byTimestampDesc() {
        return new Sort(Sort.Direction.DESC, "timestamp");
    }

}
