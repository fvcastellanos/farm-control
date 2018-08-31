package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.HumidityRead;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ArduinoDummyClient implements ArduinoClient {

    private static final Logger logger = LoggerFactory.getLogger(ArduinoDummyClient.class);

    @Override
    public Optional<HumidityRead> readHumiditySensor() {

        var value = RandomUtils.nextLong(1, 25);
        logger.info("value read: {}", value);

        var result = HumidityRead.builder()
                .value(value)
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
