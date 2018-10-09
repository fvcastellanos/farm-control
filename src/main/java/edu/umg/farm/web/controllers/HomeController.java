package edu.umg.farm.web.controllers;

import edu.umg.farm.service.InformationService;
import edu.umg.farm.web.controllers.model.DashboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private InformationService informationService;

    private int displayReadEventLimit;


    public HomeController() {
        displayReadEventLimit = 10;
    }

    @GetMapping("/")
    @ResponseBody
    public ModelAndView index() {

        var result = informationService.getLatestReadEvents(displayReadEventLimit);

        DashboardModel model;

        if (result.isRight()) {
            model = DashboardModel.builder()
                    .readEvents(result.get())
                    .build();
        } else {
            model = DashboardModel.builder()
                    .errorMessage(result.getLeft())
                    .build();
        }

        return new ModelAndView("home/index", Map.of("model", model));
    }
}
