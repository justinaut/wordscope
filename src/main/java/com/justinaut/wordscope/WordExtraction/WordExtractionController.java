package com.justinaut.wordscope.WordExtraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordExtractionController {

    @Autowired
    WordExtractorService wordExtractorService;

    @RequestMapping("/extractWords/extract")
    public WordExtraction handleGet(@RequestParam(name = "q", required = false) String input) {
        return wordExtractorService.getWordExtraction(input);
    }

}
