package edu.umg.farm.web.controllers;

import edu.umg.farm.web.controllers.model.Routes;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected Map<String, Object> getModel() {

        var map = new HashMap<String, Object>();
        map.put("ROOT", Routes.ROOT);
        map.put("MANUAL_ACTIVATION", Routes.MANUAL_ACTIVATION);
        map.put("PUMP_ACTION", Routes.PUMP_ACTION);
        map.put("READ_HUMIDITY", Routes.READ_HUMIDITY);
        map.put("READ_TEMPERATURE", Routes.READ_TEMPERATURE);

        return map;
    }
}
