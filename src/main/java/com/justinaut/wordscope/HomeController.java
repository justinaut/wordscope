package com.justinaut.wordscope;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public ModelAndView handleGet() {
        return new ModelAndView("home");
    }
}
