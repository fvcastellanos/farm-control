package edu.umg.farm.service;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.service.model.FarmContext;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FarmService {

    private static final Logger logger = LoggerFactory.getLogger(FarmService.class);

    private double humidityThreshold;
    private double temperatureThreshold;

    private ArduinoClient arduinoClient;

    private ReadEventDao readEventDao;

    public FarmService(double humidityThreshold, double temperatureThreshold, ArduinoClient arduinoClient, ReadEventDao readEventDao) {
        this.humidityThreshold = humidityThreshold;
        this.temperatureThreshold = temperatureThreshold;
        this.arduinoClient = arduinoClient;
        this.readEventDao = readEventDao;
    }

    public void checkSoil() {

        var resultHolder = performHumidityRead()
                .flatMap(this::turnOnWaterPumpIfNecessary)
                .flatMap(this::publishResult);

        resultHolder.ifPresent(result -> logger.info("soil status: {}", result));
    }

    private Optional<FarmContext> performHumidityRead() {

        logger.info("perform read from remote sensor...");

        var humidityReadHolder = arduinoClient.readHumiditySensor();

        if (humidityReadHolder.isPresent()) {

            var context = contextBuilder(false)
                    .sensorRead(humidityReadHolder.get())
                    .build();

            return Optional.of(context);
        }

        return Optional.of(contextBuilder(true)
                        .message("can't read from remote sensor")
                        .build());
    }

    private Optional<FarmContext> turnOnWaterPumpIfNecessary(FarmContext farmContext) {

        var sensorRead = farmContext.getSensorRead();
        var value = sensorRead.getHumidityValue();

        if (value < humidityThreshold) {

            logger.info("humidity factor is below accepted parameters, starting water pump");
            arduinoClient.startWaterPump();

            farmContext.setPumpActivated(true);
            return Optional.of(farmContext);
        }

        logger.info("looks like humidity is at good level");

        arduinoClient.stopWaterPump();
        farmContext.setPumpActivated(false);

        return Optional.of(farmContext);
    }

    private Optional<FarmContext> publishResult(FarmContext farmContext) {

        try {

            logger.info("publishing read result: {}", farmContext);

            var readEvent = buildReadEvent(farmContext);
            readEventDao.saveReadEvent(readEvent);

            return Optional.of(farmContext);

        } catch (Exception ex) {

            logger.error("can't read from sensor...", ex);
            return Optional.empty();
        }
    }

    private FarmContext.FarmContextBuilder contextBuilder(boolean readError) {

        return FarmContext.builder()
                .humidityThreshold(humidityThreshold)
                .temperatureThreshold(temperatureThreshold)
                .readError(readError);
    }

    private ReadEvent buildReadEvent(FarmContext farmContext) {

        var sensorRead = farmContext.getSensorRead();

        return ReadEvent.builder()
                .humidityValue(sensorRead.getHumidityValue())
                .humidityThreshold(farmContext.getHumidityThreshold())
                .temperatureValue(sensorRead.getTemperatureValue())
                .temperatureThreshold(farmContext.getTemperatureThreshold())
                .readError(farmContext.isReadError())
                .pumpActivated(farmContext.isPumpActivated())
                .message(farmContext.getMessage())
                .build();
    }

}
