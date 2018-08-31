package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.HumidityRead;

import java.util.Optional;

public interface ArduinoClient {

    Optional<HumidityRead> readHumiditySensor();

    void startWaterPump();
    void stopWaterPump();
}
