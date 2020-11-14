package com.justinaut.wordscope.WordExtraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WordExtractionController.class)
class WordExtractionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WordExtractorService mockWordExtractorService;

    @BeforeEach
    void setup() {
        when(mockWordExtractorService.getWordSet()).thenReturn(emptySet());
    }

    @Test
    void handleGet_withInputParameter() throws Exception {
        when(mockWordExtractorService.getWordExtraction(anyString())).thenReturn(new WordExtraction("scrambled string", emptyList()));

        MvcResult mvcResult = mockMvc.perform(get("/extractWords/extract?q=scrambled string"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"scrambled string\",\"words\":[]}");
    }

    @Test
    void handleGet_withoutInputParameter() throws Exception {
        when(mockWordExtractorService.getWordExtraction(any())).thenReturn(new WordExtraction("", emptyList()));

        MvcResult mvcResult = mockMvc.perform(get("/extractWords/extract"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"words\":[]}");
    }

    @Test
    void handleGet_interactsWithWordExtractorService() throws Exception {
        mockMvc.perform(get("/extractWords/extract?q=scrambled string"))
                .andExpect(status().isOk())
                .andReturn();

        verify(mockWordExtractorService, times(1)).getWordExtraction("scrambled string");
    }

    @Test
    void handleGet_viaDirectCall() {
        // This test may be deleted as it does not represent the intended use of the controller as a manager of application end points.

        when(mockWordExtractorService.getWordExtraction(anyString())).thenReturn(new WordExtraction("some string", emptyList()));

        WordExtractionController subject = new WordExtractionController();
        subject.wordExtractorService = mockWordExtractorService;

        WordExtraction extraction = subject.handleGet("some string");

        assertThat(extraction).isEqualTo(new WordExtraction("some string", emptyList()));
    }

    @Test
    void getView_returnsHtmlForInput() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/extractWords"))
                .andExpect(status().isOk())
                .andExpect(view().name("wordExtraction"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Enter a word, or word jumble, to find words");
    }

    @Test
    void getView_returnsHtmlOfOutput() throws Exception {
        ArrayList<String> words = new ArrayList<>() {{
            add("den");
            add("end");
            add("ned");
        }};

        WordExtraction wordExtraction = new WordExtraction("den", words);

        when(mockWordExtractorService.getWordExtraction("den")).thenReturn(wordExtraction);

        MvcResult mvcResult = mockMvc.perform(get("/extractWords?q=den"))
                .andExpect(status().isOk())
                .andExpect(view().name("wordExtraction"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Input: den");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("List:");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("<p>den</p>");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("<p>end</p>");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("<p>ned</p>");
    }

    @Test
    void getView_callsWordExtractorService() throws Exception {
        mockMvc.perform(get("/extractWords?q=den"))
                .andExpect(status().isOk())
                .andExpect(view().name("wordExtraction"))
                .andReturn();

        verify(mockWordExtractorService).getWordExtraction("den");
    }

}