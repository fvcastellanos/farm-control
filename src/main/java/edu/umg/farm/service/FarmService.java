package edu.umg.farm.service;

import edu.umg.farm.service.model.SensorRead;
import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.pi.client.PiClient;
import edu.umg.farm.pi.model.WaterPumpState;
import edu.umg.farm.service.model.FarmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FarmService {

    private static final Logger logger = LoggerFactory.getLogger(FarmService.class);

    private double humidityThreshold;
    private double temperatureThreshold;

    private PiClient piClient;

    private ReadEventDao readEventDao;

    public FarmService(double humidityThreshold, double temperatureThreshold, PiClient piClient, ReadEventDao readEventDao) {
        this.humidityThreshold = humidityThreshold;
        this.temperatureThreshold = temperatureThreshold;
        this.readEventDao = readEventDao;
        this.piClient = piClient;
    }

    public void checkSoil() {

        var resultHolder = performHumidityRead()
                .flatMap(this::performTemperatureRead)
                .flatMap(this::turnOnWaterPumpIfNecessary)
                .flatMap(this::publishResult);

        resultHolder.ifPresent(result -> logger.info("soil status: {}", result));
    }

    private Optional<FarmContext> performHumidityRead() {

        logger.info("perform read from remote humidity sensor...");

        var humidityReadHolder = piClient.readHumidity();

        if (humidityReadHolder.isPresent()) {

            var response = humidityReadHolder.get();
            var sensorRead = SensorRead.builder()
                    .humidityValue(response.getHumidity())
                    .build();

            var context = contextBuilder(false)
                    .sensorRead(sensorRead)
                    .build();

            return Optional.of(context);
        }

        return Optional.of(contextBuilder(true)
                        .message("can't read from humidity sensor")
                        .build());
    }

    private Optional<FarmContext> performTemperatureRead(FarmContext farmContext) {

        logger.info("perform read from remote temperature sensor...");

        var temperatureReadHolder = piClient.readTemperature();

        if (temperatureReadHolder.isPresent()) {

            var response = temperatureReadHolder.get();
            var sensorRead = farmContext.getSensorRead();

            sensorRead.setTemperatureValue(response.getTemperature());

            return Optional.of(farmContext);
        }

        return Optional.of(contextBuilder(true)
                .message("can't read from humidity sensor")
                .build());
    }

    private Optional<FarmContext> turnOnWaterPumpIfNecessary(FarmContext farmContext) {

        var sensorRead = farmContext.getSensorRead();
        var humidityValue = sensorRead.getHumidityValue();
        var temperatureValue = sensorRead.getTemperatureValue();

        if ((humidityValue < humidityThreshold) || (temperatureValue > temperatureThreshold)) {

            logger.info("humidity factor is below accepted parameters, starting water pump");

            piClient.setWaterPump(WaterPumpState.ON);

            farmContext.setPumpActivated(true);
            return Optional.of(farmContext);
        }

        logger.info("looks like humidity and temperature is at good level");

        piClient.setWaterPump(WaterPumpState.OFF);
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
