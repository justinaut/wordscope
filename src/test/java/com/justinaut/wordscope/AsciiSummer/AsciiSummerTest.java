package com.justinaut.wordscope.AsciiSummer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AsciiSummerTest {

    @Test
    void sumString_alphabetic() {
        String input = "ABCz";
        assertThat(AsciiSummer.sum(input)).isEqualTo(new AsciiSum(input, 320L));
    }

    @Test
    void sumString_numeric() {
        String input = "123";
        assertThat(AsciiSummer.sum(input)).isEqualTo(new AsciiSum(input, 150L));
    }

    @Test
    void sumString_whitespace() {
        String input = " \t";
        assertThat(AsciiSummer.sum(input)).isEqualTo(new AsciiSum(input, 41L));
    }

    @Test
    void sumString_mixed() {
        String input = "ABCz123 \t";
        assertThat(AsciiSummer.sum(input)).isEqualTo(new AsciiSum(input, 320L + 150L + 41L));
    }

    @Test
    void sumString_empty() {
        String input = "";
        assertThat(AsciiSummer.sum(input)).isEqualTo(new AsciiSum(input, 0L));
    }

    @Test
    void sumString_null() {
        assertThat(AsciiSummer.sum(null)).isEqualTo(new AsciiSum("", 0L));
    }
}