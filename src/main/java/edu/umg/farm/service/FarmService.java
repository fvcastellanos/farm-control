package edu.umg.farm.service;

import edu.umg.farm.pi.model.PiResponse;
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

        var context = contextBuilder(false)
                .build();

        var resultHolder = performHumidityRead(context)
                .flatMap(this::performTemperatureRead)
                .flatMap(this::verifyHumidityAndTemperatureLevels)
                .flatMap(this::activateWaterPump)
                .flatMap(this::publishResult);

        resultHolder.ifPresent(result -> logger.info("soil status: {}", result));
    }

    public void pumpAction(int action) {

        var pumpActivated = action == 1;

        var context = contextBuilder(false)
                .sensorRead(SensorRead.builder().build())
                .message("MANUAL ACTIVATION")
                .pumpActivated(pumpActivated)
                .build();

        activateWaterPump(context)
                .flatMap(this::publishResult);
    }

    public Optional<Double> readHumidity() {

        var context = FarmContext.builder()
                .readError(false)
                .message("MANUAL ACTIVATION")
                .build();

        return performHumidityRead(context)
                .flatMap(this::publishResult)
                .flatMap(farmContext -> {

                    if (farmContext.isReadError()) {
                        return Optional.empty();
                    }

                    return Optional.of(farmContext.getSensorRead().getHumidityValue());
                });
    }

    public Optional<Double> readTemperature() {

        var context = FarmContext.builder()
                .readError(false)
                .sensorRead(SensorRead.builder().build())
                .message("MANUAL ACTIVATION")
                .build();

        return performTemperatureRead(context)
                .flatMap(this::publishResult)
                .flatMap(farmContext -> {

                    if (farmContext.isReadError()) {
                        return Optional.empty();
                    }

                    return Optional.of(farmContext.getSensorRead().getTemperatureValue());
                });
    }
    // ----------------------------------------------------------------------------------------------------

    private Optional<FarmContext> performHumidityRead(FarmContext farmContext) {

        logger.info("perform read from remote humidity sensor...");

        var humidityReadHolder = piClient.readHumidity();

        if (humidityReadHolder.isPresent()) {

            var response = humidityReadHolder.get();
            var sensorRead = SensorRead.builder()
                    .humidityValue(response.getHumidity())
                    .build();

            farmContext.setSensorRead(sensorRead);

            return Optional.of(farmContext);
        }

        farmContext.setReadError(true);
        farmContext.setMessage("can't read from humidity sensor");

        return Optional.of(farmContext);
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

    private Optional<FarmContext> verifyHumidityAndTemperatureLevels(FarmContext farmContext) {

        var sensorRead = farmContext.getSensorRead();
        var humidityValue = sensorRead.getHumidityValue();
        var temperatureValue = sensorRead.getTemperatureValue();

        if ((humidityValue < humidityThreshold) || (temperatureValue > temperatureThreshold)) {

            logger.info("humidity factor is below accepted parameters, starting water pump");

            farmContext.setPumpActivated(true);
            return Optional.of(farmContext);
        }

        logger.info("looks like humidity and temperature is at good level");

        farmContext.setPumpActivated(false);
        return Optional.of(farmContext);
    }

    private Optional<FarmContext> activateWaterPump(FarmContext farmContext) {

        logger.info("verifying if pump should be activated");

        if (farmContext.isPumpActivated()) {

            logger.info("water pump activated");
            piClient.setWaterPump(WaterPumpState.ON);

            return Optional.of(farmContext);
        }

        logger.info("water pump deactivated");
        piClient.setWaterPump(WaterPumpState.OFF);

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

    private FarmContext.Builder contextBuilder(boolean readError) {

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
