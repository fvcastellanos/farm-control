package edu.umg.farm.service;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.arduino.model.HumidityRead;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FarmService {

    private static final Logger logger = LoggerFactory.getLogger(FarmService.class);

    private ArduinoClient arduinoClient;

    public FarmService(ArduinoClient arduinoClient) {
        this.arduinoClient = arduinoClient;
    }

    public void checkSoil() {

        performHumidityRead()
                .flatMap(this::cosaUno);
    }

    private Either<String, HumidityRead> performHumidityRead() {

        logger.info("perform read from remote sensor...");

        return arduinoClient.readHumiditySensor();
    }

    private Either<String, HumidityRead> cosaUno(HumidityRead humidityRead) {

        var value = humidityRead.getValue();

        if (value < 10) {

            logger.info("humidity factor is below accepted parameters, starting water pump");
            // start water pump
        }

        return Either.right(humidityRead);

    }

}
