package com.justinaut.wordscope.WordExtraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordExtractionController {

    @Autowired
    WordBankProvider wordBankProvider;

    @RequestMapping("/extractWords")
    public WordExtraction handleGet(@RequestParam(name = "in", required = false) String input) {
        if (input == null) {
            input = "";
        }

        WordExtractor wordExtractor = new WordExtractor();
        return wordExtractor.extractWords(input, wordBankProvider.getWordSet());
    }

}
