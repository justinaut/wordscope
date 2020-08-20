package com.justinaut.wordscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

class WordExtractorTest {

    WordExtractor subject;
    Set<String> wordSet = Set.of("eve", "veer", "nerve", "even", "ever", "zen", "never");

    @BeforeEach
    void setup() {
        subject = new WordExtractor();
    }

    @Test
    @DisplayName("Word extractor returns an extraction object containing the given string")
    void extractWords_returnsGivenString() {
        WordExtraction actual = subject.extractWords("some given string", wordSet);

        assertThat(actual.getInput()).isEqualTo("some given string");
    }

    @Test
    @DisplayName("Word extractor applies toLowerCase() to input string")
    void extractWords_returnsGivenStringInLowerCase() {
        WordExtraction actual = subject.extractWords("ALL CAPS", wordSet);

        assertThat(actual.getInput()).isEqualTo("all caps");
    }

    @Test
    @DisplayName("Word extractor returns an extraction object containing a list of words containing letters from the input")
    void extractWords_longList() {
        WordExtraction actual = subject.extractWords("rvnee", wordSet);

        assertThat(actual.getWords()).containsExactly("nerve", "never", "even", "ever", "veer", "eve");
    }

    @Test
    void extractWords_emptyWordSet() {
        WordExtraction actual = subject.extractWords("eerv", emptySet());

        assertThat(actual.getWords()).isEmpty();
    }

    @Test
    void extractWords_nullInputString() {
        WordExtraction actual = subject.extractWords(null, wordSet);

        assertThat(actual.getInput()).isEqualTo("");
    }

    @Test
    void extractWords_nullWordSet() {
        WordExtraction actual = subject.extractWords("eerv", null);

        assertThat(actual.getWords()).isEmpty();
    }

    @Test
    @DisplayName("A count of letters are produced with a given string")
    void makeLetterInventory_nonEmptyString() {
        Map<Character, Integer> characterIntegerMap = subject.makeLetterInventory("rifflandia");

        assertThat(characterIntegerMap).isEqualTo(
                Map.of(
                        'n', 1,
                        'a', 2,
                        'i', 2,
                        'd', 1,
                        'f', 2,
                        'l', 1,
                        'r', 1)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n" })
    @DisplayName("Blank strings (empty or whitespace) produces an empty letter inventory")
    void makeLetterInventory_emptyInput(String input) {
        assertThat(subject.makeLetterInventory(input)).isEqualTo(emptyMap());
    }

    @Test
    @DisplayName("Mixed input (blank and non-blank) produces a letter inventory without whitespace characters")
    void makeLetterInventory_mixedInput() {
        Map<Character, Integer> characterIntegerMap = subject.makeLetterInventory("\t tabbed \n the end");

        assertThat(characterIntegerMap).isEqualTo(
                Map.of(
                        't', 2,
                        'a', 1,
                        'b', 2,
                        'd', 2,
                        'e', 3,
                        'h', 1,
                        'n', 1)
        );
    }

    @Test
    @DisplayName("makeLetterInventory permits upper case and lower case")
    void makeLetterInventory_permitsMixedCase() {
        Map<Character, Integer> characterIntegerMap = subject.makeLetterInventory("JUST because");

        assertThat(characterIntegerMap).isEqualTo(
                Map.of(
                        'J', 1,
                        'S', 1,
                        'T', 1,
                        'U', 1,
                        'a', 1,
                        'b', 1,
                        'c', 1,
                        'e', 2,
                        's', 1,
                        'u', 1)
        );
    }

    @Test
    @DisplayName("A null string produces an empty letter inventory")
    void makeLetterInventory_nullInput() {
        Map<Character, Integer> characterIntegerMap = subject.makeLetterInventory(null);

        assertThat(characterIntegerMap).isEqualTo(emptyMap());
    }

    @Test
    void isSubWord_exactMatch() {
        assertThat(subject.isSubWord("samestring", "samestring")).isTrue();
    }

    @Test
    void isSubWord_anagram() {
        assertThat(subject.isSubWord("never", "nerve")).isTrue();
    }

    @Test
    void isSubWord_lengthMismatch() {
        assertThat(subject.isSubWord("even", "steven")).isFalse();
    }

    @Test
    void isSubWord_letterMismatch() {
        assertThat(subject.isSubWord("even", "zen")).isFalse();
    }

    @Test
    void isSubWord_emptyInputs() {
        assertThat(subject.isSubWord("", "")).isFalse();
    }

    @Test
    void isSubWord_emptySupplyWord() {
        assertThat(subject.isSubWord("supply", "")).isFalse();
    }

    @Test
    void isSubWord_emptyDemandWord() {
        assertThat(subject.isSubWord("", "demand")).isFalse();
    }

    @Test
    void isSubWord_whiteSpaceInputs() {
        assertThat(subject.isSubWord("\t\t", "\t\t\t")).isFalse();
    }

    @Test
    void isSubWord_nullSupplyString() {
        assertThat(subject.isSubWord(null, "demand")).isFalse();
    }

    @Test
    void isSubWord_nullDemandString() {
        assertThat(subject.isSubWord("supply", null)).isFalse();
    }

    @Test
    void orderByLengthAndAlpha_emptyInputList() {
        assertThat(subject.orderByLengthAndAlpha(emptyList())).isEqualTo(emptyList());
    }

    @Test
    void orderByLengthAndAlpha_singleItemInputList() {
        assertThat(subject.orderByLengthAndAlpha(List.of("single"))).isEqualTo(List.of("single"));
    }

    @Test
    void orderByLengthAndAlpha_alphaSortInputList() {
        assertThat(subject.orderByLengthAndAlpha(List.of("single", "double"))).isEqualTo(List.of("double", "single"));
    }

    @Test
    void orderByLengthAndAlpha_lengthAndAlphaSortInputList() {
        assertThat(subject.orderByLengthAndAlpha(List.of("single", "double", "triple", "quadruple"))).isEqualTo(List.of("quadruple", "double", "single", "triple"));
    }


}