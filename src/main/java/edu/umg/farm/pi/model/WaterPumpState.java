package edu.umg.farm.pi.model;

public enum WaterPumpState {

    ON(1),
    OFF(0);

    private int value;

    WaterPumpState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
