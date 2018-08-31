package edu.umg.farm.arduino;

import edu.umg.farm.arduino.model.HumidityRead;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArduinoDummyClient implements ArduinoClient {

    private static final Logger logger = LoggerFactory.getLogger(ArduinoDummyClient.class);

    @Override
    public Either<String, HumidityRead> readHumiditySensor() {

        return Try.of(() -> {

            var value = RandomUtils.nextLong(1, 25);
            logger.info("value read: {}", value);

            return HumidityRead.builder()
                    .value(value)
                    .build();

        }).onSuccess(read -> logger.info("value read: {}", read))
                .onFailure(ex -> logger.error("can't read from external sensor: ", ex))
                .toEither()
                .mapLeft(ex -> "can't read from sensor");
    }
}
