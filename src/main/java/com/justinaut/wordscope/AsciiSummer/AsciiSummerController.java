package com.justinaut.wordscope.AsciiSummer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AsciiSummerController {

    @GetMapping(value="/asciiSum", produces="text/html")
    public ModelAndView getView(@RequestParam(name = "q", required = false) String toSum) {

        Map<String, AsciiSum> model = new HashMap<>();
        model.put("asciiSum", AsciiSummer.sum(toSum));

        return new ModelAndView("asciiSum", model);
    }

    @GetMapping(value="/asciiSum/sum", produces="application/json")
    public AsciiSum getAsciiSum(@RequestParam(name = "q", required = false) String toSum) {

        return AsciiSummer.sum(toSum);
    }
}
