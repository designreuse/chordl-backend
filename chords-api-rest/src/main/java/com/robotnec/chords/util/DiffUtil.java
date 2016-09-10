package com.robotnec.chords.util;

import com.robotnec.chords.persistence.entity.Song;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zak <zak@robotnec.com>
 */
@UtilityClass
public class DiffUtil {

    public static List<String> split(Song song) {
        List<String> lines = new ArrayList<>();
        lines.add(song.getTitle());
        lines.addAll(Arrays.asList(song.getLyrics().split("\n")));
        return lines;
    }

    public static Song join(List<String> lines) {
        String title = lines.get(0);
        String lyrics = lines.subList(1, lines.size()).stream().collect(Collectors.joining("\n"));
        return Song.builder()
                .title(title)
                .lyrics(lyrics)
                .build();
    }
}
