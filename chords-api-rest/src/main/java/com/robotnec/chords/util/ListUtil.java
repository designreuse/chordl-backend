package com.robotnec.chords.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zak <zak@robotnec.com>
 */
@UtilityClass
public class ListUtil {

    public static <T> List<T> greedyTakeWhile(List<T> source, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : source) {
            result.add(item);
            if (!predicate.test(item)) break;
        }
        return result;
    }
}
