package com.ruben.rfaf.shared.thymeleaf;

import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.shared.security.MyController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@AllArgsConstructor
public class TemplateController {

    private MyController myController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        Referee referee = myController.logged();
        if (referee != null) {
            mav.addObject("user", myController.logged());
        }
        return mav;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView game() {
        ModelAndView mav = new ModelAndView("game");
        Referee referee = myController.logged();
        if (referee != null) {
            mav.addObject("user", myController.logged());

        }
        return mav;
    }

    @RequestMapping(value = "/categoryCrud", method = RequestMethod.GET)
    public ModelAndView categoryCrud() {

        if (myController.isAdmin()) {
            return new ModelAndView("categoryCrud");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/refereeCrud", method = RequestMethod.GET)
    public ModelAndView refereeCrud() {
        if (myController.isAdmin()) {
            return new ModelAndView("refereeCrud");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/teamCrud", method = RequestMethod.GET)
    public ModelAndView teamCrud() {
        if (myController.isAdmin()) {
            return new ModelAndView("teamCrud");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/playerCrud", method = RequestMethod.GET)
    public ModelAndView playerCrud() {
        if (myController.isAdmin()) {
            return new ModelAndView("playerCrud");
        }
        return new ModelAndView("login");

    }

    @RequestMapping(value = "/competitionCrud", method = RequestMethod.GET)
    public ModelAndView competitionCrud() {
        if (myController.isAdmin()) {
            return new ModelAndView("competitionCrud");
        }
        return new ModelAndView("login");

    }

    @RequestMapping(value = "/matchCrud", method = RequestMethod.GET)
    public ModelAndView matchCrud() {
        if (myController.isAdmin()) {
            return new ModelAndView("matchCrud");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/designationCrud", method = RequestMethod.GET)
    public ModelAndView designationCud() {
        if (myController.isAdmin()) {
            return new ModelAndView("designationCrud");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/acta", method = RequestMethod.GET)
    public ModelAndView acta(@RequestParam String acta_id) {
        Referee referee = myController.logged();
        if (!myController.isActaCerrada(acta_id)) {
            if (myController.isRefereeActa(acta_id)) {
                ModelAndView mav = new ModelAndView("acta");
                if (referee != null) {
                    mav.addObject("user", referee);
                }
                return mav;
            }

        }
        ModelAndView mav = new ModelAndView("schedule");
        if (referee != null) {
            mav.addObject("user", referee);

        }
        return mav;
    }

    @RequestMapping(value = "/partials/actaJugador", method = RequestMethod.GET)
    public ModelAndView partialasdasd() {
        return new ModelAndView("partials/actaJugador");
    }

    @RequestMapping(value = "/partials/gameJugador", method = RequestMethod.GET)
    public ModelAndView gameJugador() {
        return new ModelAndView("partials/gameJugador");
    }

    @RequestMapping(value = "/partials/partido", method = RequestMethod.GET)
    public ModelAndView partido() {
        return new ModelAndView("partials/partido");
    }

    @RequestMapping(value = "/partidos", method = RequestMethod.GET)
    public ModelAndView games() {
        ModelAndView mav = new ModelAndView("schedule");
        Referee referee = myController.logged();
        if (referee != null) {
            mav.addObject("user", myController.logged());

        }
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
