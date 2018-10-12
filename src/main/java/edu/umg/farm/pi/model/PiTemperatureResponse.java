package edu.umg.farm.pi.model;

import lombok.Getter;
import lombok.Setter;

public class PiTemperatureResponse extends PiResponse {

    @Getter
    @Setter
    private double temperature;
}
