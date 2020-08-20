package com.justinaut.wordscope.WordExtraction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WordExtraction {
    private String input;
    private List<String> words;
}
