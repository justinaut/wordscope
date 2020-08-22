package com.justinaut.wordscope.WordExtraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WordExtractorServiceTest {
    private WordExtractorService subject;

    @MockBean
    ResourceLoader resourceLoader;

    @BeforeEach
    void setup() {
        subject = new WordExtractorService(resourceLoader);
    }

    @Test
    @DisplayName("Word set is loaded from file")
    void load_loadsWordSet() throws IOException {
        when(resourceLoader.getResource(anyString())).thenReturn(new ClassPathResource("static/test_words.txt"));

        assertThat(subject.getWordSet()).isEmpty();
        subject.load();
        assertThat(subject.getWordSet()).containsExactly("four", "one", "three", "two");
    }

    @Test
    @DisplayName("Load method calls the correct file name (which is hard-coded in class)")
    void load_loadsCorrectFileName() throws IOException {
        when(resourceLoader.getResource(anyString())).thenReturn(new ClassPathResource("static/test_words.txt"));
        subject.load();

        verify(resourceLoader, times(1)).getResource("classpath:static/words_alpha.txt");
    }

    @Test
    @DisplayName("Throws exception when a file resource cannot be loaded")
    void load_resourceFileNotFound() {
        when(resourceLoader.getResource(anyString())).thenReturn(new ClassPathResource("classpath:static/file_does_not_exist.txt"));

        FileNotFoundException fileNotFoundException = assertThrows(
                FileNotFoundException.class,
                () -> subject.load()
        );
        assertThat(fileNotFoundException.getMessage()).isEqualTo("File not found: file_does_not_exist.txt");
    }

    @Test
    @DisplayName("getWordExtraction returns a WordExtraction containing subwords from an input string")
    void getWordExtraction_stringInput() throws IOException {
        when(resourceLoader.getResource(anyString())).thenReturn(new ClassPathResource("static/test_words.txt"));
        subject.load();

        WordExtraction actualExtraction = subject.getWordExtraction("wotone");

        assertThat(actualExtraction).isEqualTo(new WordExtraction("wotone", List.of("one", "two")));
    }
}