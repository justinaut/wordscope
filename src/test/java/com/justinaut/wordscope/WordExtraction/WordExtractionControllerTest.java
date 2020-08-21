package com.justinaut.wordscope.WordExtraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        MvcResult mvcResult = mockMvc.perform(get("/extractWords?in=scrambled string"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"scrambled string\",\"words\":[]}");
    }

    @Test
    void handleGet_withoutInputParameter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/extractWords"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"words\":[]}");
    }

    @Test
    void handleGet_viaDirectCall() {

        // This test may be deleted as it does not represent the intended use of the controller as a manager of application end points.

        WordExtractionController subject = new WordExtractionController();
        subject.wordExtractorService = mockWordExtractorService;

        WordExtraction extraction = subject.handleGet("some string");

        assertThat(extraction).isEqualTo(new WordExtraction("some string", emptyList()));
    }

}