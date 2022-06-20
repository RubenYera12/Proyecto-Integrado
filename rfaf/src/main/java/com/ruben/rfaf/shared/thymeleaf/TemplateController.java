package com.ruben.rfaf.shared.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class TemplateController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("standardDate", new Date());
        return mav;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView game() {
        return new ModelAndView("game");
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
    public ModelAndView competitionCrud() {
        return new ModelAndView("competitionCrud");
    }

    @RequestMapping(value = "/matchCrud", method = RequestMethod.GET)
    public ModelAndView matchCrud() {
        return new ModelAndView("matchCrud");
    }

    @RequestMapping(value = "/designationCrud",method = RequestMethod.GET)
    public ModelAndView designationCud(){
        return new ModelAndView("designationCrud");
    }

    @RequestMapping(value = "/acta",method = RequestMethod.GET)
    public ModelAndView acta(){
        return new ModelAndView("acta");
    }

    @RequestMapping(value = "/partials/actaJugador",method = RequestMethod.GET)
    public ModelAndView partialasdasd(){
        return new ModelAndView("partials/actaJugador");
    }

    @RequestMapping(value = "/partials/gameJugador",method = RequestMethod.GET)
    public ModelAndView gameJugador(){
        return new ModelAndView("partials/gameJugador");
    }
}
