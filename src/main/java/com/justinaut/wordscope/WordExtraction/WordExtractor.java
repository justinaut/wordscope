package com.justinaut.wordscope.WordExtraction;

import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

// WordExtractor is meant to receive strings composed of lower-case alphabetic ascii characters.
// Characters not meeting this criteria will mostly be ignored.
public class WordExtractor {

    public static WordExtraction extractWords(String input, Set<String> wordSet) {
        if (input == null) {
            input = "";
        }
        if (wordSet == null) {
            wordSet = emptySet();
        }

        String finalizedInput = input.toLowerCase();
        List<String> subWords = wordSet.stream()
                .filter(word -> isSubWord(finalizedInput, word))
                .collect(toList());

        return new WordExtraction(finalizedInput, orderByLengthAndAlpha(subWords));
    }

    public static Map<Character, Integer> makeLetterInventory(String input) {
        if (input == null || input.isEmpty() || input.isBlank()) {
            return emptyMap();
        }

        Map<Character, Integer> letterInventory = new HashMap<>();

        char[] charArray = input.toCharArray();
        for (char c : charArray) {
            if (!Character.isWhitespace(c)) {
                letterInventory.put(c, letterInventory.getOrDefault(c, 0) + 1);
            }
        }

        return letterInventory;
    }

    public static boolean isSubWord(String supply, String demand) {
        if (supply == null || demand == null
                || supply.isBlank() || demand.isBlank()) {
            return false;
        }

        Map<Character, Integer> supplyLetters = makeLetterInventory(supply);
        Map<Character, Integer> demandLetters = makeLetterInventory(demand);

        for (char c : demandLetters.keySet()) {
            if (supplyLetters.containsKey(c)) {
                if (0 > supplyLetters.get(c) - demandLetters.get(c)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public static List<String> orderByLengthAndAlpha(List<String> listToBeOrdered) {
        if (listToBeOrdered.size() == 1) {
            return listToBeOrdered;
        }

        Map<Integer, List<String>> wordsByLength = listToBeOrdered.stream()
                .collect(groupingBy(String::length));

        List<String> outputList = new ArrayList<>();
        wordsByLength.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .forEach(length -> outputList.addAll(wordsByLength.get(length).stream()
                        .sorted()
                        .collect(toList()))
                );

        return outputList;
    }
}
