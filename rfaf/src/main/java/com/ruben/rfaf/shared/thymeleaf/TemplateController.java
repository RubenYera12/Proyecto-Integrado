package com.ruben.rfaf.shared.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class TemplateController {

//    @RequestMapping("/")
//    public String index(Model model) {
//        model.addAttribute("fecha","xdfxsdfsdf");
//        return "index";
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mesindexsages() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping("/game")
    public String game(Model model) {
        return "game";
    }

    @RequestMapping("/categoryCrud")
    public String categoryCrud(Model model) {
        return "categoryCrud";
    }

    @RequestMapping("/refereeCrud")
    public String refereeCrud(Model model) {
        return "refereeCrud";
    }
}
