package com.justinaut.wordscope.AsciiSummer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AsciiSummerController.class)
class AsciiSummerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGet_withStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum?q=ABCz123"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"ABCz123\",\"sum\":470}");
    }

    @Test
    void handleGet_emptyStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum?q="))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"sum\":0}");
    }

    @Test
    void handleGet_withoutStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"sum\":0}");
    }
}