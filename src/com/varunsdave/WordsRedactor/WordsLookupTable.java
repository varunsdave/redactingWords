package com.varunsdave.WordsRedactor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Used to generate words look up table that acts as a quick way to see
 * if a word exists.
 *
 * This is in memory, can be replaced in future with a more persistance
 * or cached database with fast lookup.
 */
public class WordsLookupTable {
    static Set<String> generateReactingWordsSet(List<String> words) {
        Set<String> redactingWordsSet = new HashSet<>();
        for (String s : words) {
            if (!redactingWordsSet.contains(s)) {
                redactingWordsSet.add(s);
            }
        }
        return redactingWordsSet;
    }
}
