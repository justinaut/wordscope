package com.justinaut.wordscope.AsciiSummer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsciiSummerController {

    @RequestMapping("/asciiSum")
    public AsciiSum handleGet(@RequestParam(name = "q", required = false) String toSum) {

        return AsciiSummer.sum(toSum);
    }
}
