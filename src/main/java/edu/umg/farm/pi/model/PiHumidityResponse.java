package edu.umg.farm.pi.model;

import lombok.Getter;
import lombok.Setter;

public class PiHumidityResponse extends PiResponse {

    @Getter
    @Setter
    private double humidity;
}
