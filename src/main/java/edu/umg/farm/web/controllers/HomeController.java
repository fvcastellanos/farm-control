package edu.umg.farm.web.controllers;

import edu.umg.farm.dao.model.PumpActivation;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.dao.model.ValueRead;
import edu.umg.farm.service.InformationService;
import edu.umg.farm.web.controllers.model.DashboardModel;
import edu.umg.farm.web.controllers.model.Routes;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Routes.ROOT)
public class HomeController {

    @Autowired
    private InformationService informationService;

    private int displayReadEventLimit;

    public HomeController() {
        displayReadEventLimit = 10;
    }

    @GetMapping
    public ModelAndView index() {

        var model = buildDashboardModel(displayReadEventLimit);

        return new ModelAndView("home/index", Map.of("model", model));
    }

    @PostMapping
    public ModelAndView updateResults(int display) {

        this.displayReadEventLimit = display;
        var model = buildDashboardModel(displayReadEventLimit);
        return new ModelAndView("home/index", Map.of("model", model));
    }

    // --------------------------------------------------------------------------------------------------

    private DashboardModel buildDashboardModel(int limit) {

        var readEventsResult = informationService.getLatestReadEvents(limit);
        var tempEventsResult = informationService.getTemperatureEvents(limit);
        var humEventsResult = informationService.getHumidityEvents(limit);
        var pumpActionResult = informationService.getPumpActions();

        return DashboardModel.builder()
                .readEvents(buildReadEventsModel(readEventsResult))
                .temperatureReads(buildValueReads(tempEventsResult))
                .humidityReads(buildValueReads(humEventsResult))
                .pumpActivations(buildPumpActivations(pumpActionResult))
                .displayLimit(limit)
                .build();
    }

    private List<ReadEvent> buildReadEventsModel(Either<String, List<ReadEvent>> result) {

        if (result.isLeft()) {

            return Collections.emptyList();
        }

        return result.get();
    }

    private List<ValueRead> buildValueReads(Either<String, List<ValueRead>> result) {

        if (result.isLeft()) {

            return Collections.emptyList();
        }

        return result.get();
    }

    private PumpActivation buildPumpActivations(Either<String, PumpActivation> result) {

        if (result.isLeft()) {

            return null;
        }

        return result.get();
    }

}
