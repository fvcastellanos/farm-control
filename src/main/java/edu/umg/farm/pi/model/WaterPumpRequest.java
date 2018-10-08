package edu.umg.farm.pi.model;

import lombok.Getter;

public class WaterPumpRequest {

    @Getter
    private int action;

    public WaterPumpRequest(int action) {
        this.action = action;
    }
}
