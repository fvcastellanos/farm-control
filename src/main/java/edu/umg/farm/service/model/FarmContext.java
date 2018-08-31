package edu.umg.farm.service.model;

import edu.umg.farm.arduino.model.HumidityRead;
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

    private HumidityRead humidityRead;
    private boolean readError;
    private String message;
    private long humidityThreshold;
    private boolean pumpActivated;
}
