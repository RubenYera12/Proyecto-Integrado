package com.ruben.rfaf.shared.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/game")
    public String game(Model model) {
        return "game";
    }

    @RequestMapping("/categoryCrud")
    public String categoryCrud(Model model) {
        return "categoryCrud";
    }
}
