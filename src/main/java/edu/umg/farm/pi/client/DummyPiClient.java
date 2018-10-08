package edu.umg.farm.pi.client;

import edu.umg.farm.pi.model.PiHumidityResponse;
import edu.umg.farm.pi.model.PiResponse;
import edu.umg.farm.pi.model.PiTemperatureResponse;
import edu.umg.farm.pi.model.WaterPumpState;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class DummyPiClient implements PiClient {

    private Logger logger = LoggerFactory.getLogger(DummyPiClient.class);

    @Override
    public Optional<PiHumidityResponse> readHumidity() {

        logger.info("reading humidity from dummy client");
        var response = buildDummyHumidityResponse();

        if (response.getHumidity() > 0) {
            return Optional.of(response);
        }

        return Optional.empty();
    }

    @Override
    public Optional<PiTemperatureResponse> readTemperature() {

        logger.info("reading temperature from dummy client");
        var response = buildDummyTemperatureResponse();

        if (response.getTemperature() > 0) {
            return Optional.of(response);
        }

        return Optional.empty();
    }

    @Override
    public Optional<PiResponse> setWaterPump(WaterPumpState state) {

        logger.info("actuating on dummy water pump");

        return Optional.of(buildDummyWaterPumpResponse());
    }

    // ----------------------------------------------------------------------------------------

    private String getRequestId() {
        return UUID.randomUUID().toString();
    }

    private PiHumidityResponse buildDummyHumidityResponse() {

        var response = new PiHumidityResponse();
        response.setRequestId(getRequestId());
        response.setHumidity(RandomUtils.nextDouble(0, 90));

        return response;
    }

    private PiTemperatureResponse buildDummyTemperatureResponse() {

        var response = new PiTemperatureResponse();
        response.setRequestId(getRequestId());
        response.setTemperature(RandomUtils.nextDouble(0, 35));

        return response;
    }

    private PiResponse buildDummyWaterPumpResponse() {

        var response = new PiResponse();
        response.setRequestId(getRequestId());

        return response;
    }

}
