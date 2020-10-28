package com.justinaut.wordscope.AsciiSummer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AsciiSummerController.class)
class AsciiSummerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAsciiSum_withStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum/sum?q=ABCz123"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"ABCz123\",\"sum\":470}");
    }

    @Test
    void getAsciiSum_emptyStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum/sum?q="))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"sum\":0}");
    }

    @Test
    void getAsciiSum_withoutStringToSum() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum/sum"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"input\":\"\",\"sum\":0}");
    }

    @Test
    void getView_returnsHtmlForInput() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum"))
                .andExpect(status().isOk())
                .andExpect(view().name("asciiSum"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Enter a string to sum");
    }

    @Test
    void getView_returnsHtmlOfOutput() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/asciiSum?q=abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("asciiSum"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Input: abc");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Sum: 294");
    }
}