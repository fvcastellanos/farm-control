package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.SensorRead;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ArduinoDummyClient implements ArduinoClient {

    private static final Logger logger = LoggerFactory.getLogger(ArduinoDummyClient.class);

    @Override
    public Optional<SensorRead> readHumiditySensor() {

        var humidityValue = RandomUtils.nextDouble(1, 25);
        var temperatureValue = RandomUtils.nextDouble(1, 25);
        logger.info("temperature: {}, humidity: {} read", temperatureValue, humidityValue);

        var result = SensorRead.builder()
                .temperatureValue(temperatureValue)
                .humidityValue(humidityValue)
                .build();

        return Optional.of(result);
    }

    @Override
    public void startWaterPump() {
        logger.info("water pump started");
    }

    @Override
    public void stopWaterPump() {
        logger.info("water pump stopped");
    }
}
