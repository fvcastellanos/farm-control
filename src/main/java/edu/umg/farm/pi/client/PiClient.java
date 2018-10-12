package edu.umg.farm.pi.client;

import edu.umg.farm.pi.model.PiHumidityResponse;
import edu.umg.farm.pi.model.PiResponse;
import edu.umg.farm.pi.model.PiTemperatureResponse;
import edu.umg.farm.pi.model.WaterPumpState;

import java.util.Optional;

public interface PiClient {

    Optional<PiHumidityResponse> readHumidity();
    Optional<PiTemperatureResponse> readTemperature();
    Optional<PiResponse> setWaterPump(WaterPumpState state);
}
