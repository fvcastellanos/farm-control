package edu.umg.farm.web.controllers;

import edu.umg.farm.service.FarmService;
import edu.umg.farm.web.controllers.model.ManualActivationModel;
import edu.umg.farm.web.controllers.model.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping(Routes.MANUAL_ACTIVATION)
public class ManualActivationController extends BaseController {

    private int action;
    private double humidity;
    private double temperature;

    @Autowired
    private FarmService farmService;

    public ManualActivationController() {
        this.action = 0;
        this.humidity = 0;
        this.temperature = 0;
    }

    @GetMapping
    public ModelAndView index() {

        var model = buildModel();
        return new ModelAndView("activation/index", model);
    }

    @PostMapping(Routes.PUMP_ACTION)
    public RedirectView pumpAction(int action) {

        farmService.pumpAction(action);

        this.action = action;
        return new RedirectView(Routes.MANUAL_ACTIVATION);
    }

    @PostMapping(Routes.READ_HUMIDITY)
    public RedirectView readHumidity() {

        var humidityHolder = farmService.readHumidity();

        this.humidity = humidityHolder.isPresent() ? humidityHolder.get() : -1;
        return new RedirectView(Routes.MANUAL_ACTIVATION);
    }

    @PostMapping(Routes.READ_TEMPERATURE)
    public RedirectView readTemperature() {

        var temperatureHolder = farmService.readTemperature();

        this.temperature = temperatureHolder.isPresent() ? temperatureHolder.get() : -1;
        return new RedirectView(Routes.MANUAL_ACTIVATION);
    }

    // -------------------------------------------------------------------------------------------

    private Map<String, Object> buildModel() {

        var activationModel = ManualActivationModel.builder()
                .action(this.action)
                .humidity(this.humidity)
                .temperature(this.temperature)
                .build();

        var model = getModel();
        model.put("model", activationModel);

        return model;
    }

}
