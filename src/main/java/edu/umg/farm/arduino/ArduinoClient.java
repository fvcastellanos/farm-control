package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.HumidityRead;
import io.vavr.control.Either;

public interface ArduinoClient {

    Either<String, HumidityRead> readHumiditySensor();
}
