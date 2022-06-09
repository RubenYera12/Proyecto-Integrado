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
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView game() {
        ModelAndView mav = new ModelAndView("game");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/categoryCrud", method = RequestMethod.GET)
    public ModelAndView categoryCrud() {
        ModelAndView mav = new ModelAndView("categoryCrud");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/refereeCrud", method = RequestMethod.GET)
    public ModelAndView refereeCrud() {
        ModelAndView mav = new ModelAndView("refereeCrud");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/teamCrud", method = RequestMethod.GET)
    public ModelAndView teamCrud() {
        ModelAndView mav = new ModelAndView("teamCrud");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/playerCrud", method = RequestMethod.GET)
    public ModelAndView playerCrud() {
        return new ModelAndView("playerCrud");
    }

    @RequestMapping(value = "/competitionCrud", method = RequestMethod.GET)
    public ModelAndView competitionCrud(){
        return new ModelAndView("competitionCrud");
    }

}
