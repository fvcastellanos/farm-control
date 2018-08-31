package edu.umg.farm.service;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.arduino.model.HumidityRead;
import edu.umg.farm.service.model.FarmContext;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FarmService {

    private static final Logger logger = LoggerFactory.getLogger(FarmService.class);

    private long humidityThreshold;

    private ArduinoClient arduinoClient;

    public FarmService(long humidityThreshold, ArduinoClient arduinoClient) {
        this.humidityThreshold = humidityThreshold;
        this.arduinoClient = arduinoClient;
    }

    public void checkSoil() {

        performHumidityRead()
                .flatMap(this::turnOnWaterPumpIfNecessary)
                .flatMap(this::publishResult);

    }

    private Optional<FarmContext> performHumidityRead() {

        logger.info("perform read from remote sensor...");

        var humidityReadHolder = arduinoClient.readHumiditySensor();

        if (humidityReadHolder.isPresent()) {

            var context = contextBuilder(false)
                    .humidityRead(humidityReadHolder.get())
                    .build();

            return Optional.of(context);
        }

        return Optional.of(contextBuilder(true)
                        .message("can't read from remote sensor")
                        .build());
    }

    private Optional<FarmContext> turnOnWaterPumpIfNecessary(FarmContext farmContext) {

        var humidityRead = farmContext.getHumidityRead();
        var value = humidityRead.getValue();

        if (value < humidityThreshold) {

            logger.info("humidity factor is below accepted parameters, starting water pump");
            arduinoClient.startWaterPump();

            farmContext.setPumpActivated(true);
            return Optional.of(farmContext);
        }

        logger.info("looks like humidity is at good level");
        farmContext.setPumpActivated(false);
        return Optional.of(farmContext);
    }

    private Optional<FarmContext> publishResult(FarmContext farmContext) {

        logger.info("publishing read result: {}", farmContext);
        return Optional.of(farmContext);

    }

    private FarmContext.FarmContextBuilder contextBuilder(boolean readError) {

        return FarmContext.builder()
                .humidityThreshold(humidityThreshold)
                .readError(readError);
    }

}
