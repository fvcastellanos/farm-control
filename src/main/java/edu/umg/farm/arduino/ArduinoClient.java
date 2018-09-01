package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.SensorRead;

import java.util.Optional;

public interface ArduinoClient {

    Optional<SensorRead> readHumiditySensor();

    void startWaterPump();
    void stopWaterPump();
}
