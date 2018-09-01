package edu.umg.farm.arduino.model;

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
public class SensorRead {

    private double humidityValue;
    private double temperatureValue;
}