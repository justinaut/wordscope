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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WordBankProviderTest {
    private WordBankProvider subject;

    @MockBean
    ResourceLoader resourceLoader;

    @BeforeEach
    void setup() {
        subject = new WordBankProvider(resourceLoader);
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

}