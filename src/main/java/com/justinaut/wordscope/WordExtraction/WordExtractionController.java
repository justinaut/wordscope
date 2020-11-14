package com.justinaut.wordscope.WordExtraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WordExtractionController {

    @Autowired
    WordExtractorService wordExtractorService;

    @GetMapping("/extractWords")
    public ModelAndView getView(@RequestParam(name = "q", required = false) String input) {

        Map<String, WordExtraction> model = new HashMap<>();
        model.put("wordExtraction", wordExtractorService.getWordExtraction(input));

        return new ModelAndView("wordExtraction", model);
    }

    @GetMapping("/extractWords/extract")
    public WordExtraction handleGet(@RequestParam(name = "q", required = false) String input) {
        return wordExtractorService.getWordExtraction(input);
    }

}
