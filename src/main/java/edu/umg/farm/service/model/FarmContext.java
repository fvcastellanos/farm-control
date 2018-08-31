package edu.umg.farm.service.model;

import edu.umg.farm.arduino.model.HumidityRead;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmContext {

    private HumidityRead humidityRead;

}
