package edu.umg.farm.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class FarmContext {

    private SensorRead sensorRead;
    private boolean readError;
    private String message;
    private double humidityThreshold;
    private double temperatureThreshold;
    private boolean pumpActivated;
}
