package edu.umg.farm.pi.client;

import edu.umg.farm.pi.model.PiHumidityResponse;
import edu.umg.farm.pi.model.PiResponse;
import edu.umg.farm.pi.model.PiTemperatureResponse;
import edu.umg.farm.pi.model.WaterPumpRequest;
import edu.umg.farm.pi.model.WaterPumpState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class RestPiClient implements PiClient {

    private Logger logger = LoggerFactory.getLogger(RestPiClient.class);

    private String baseUri;
    private RestTemplate restTemplate;

    public RestPiClient(RestTemplate restTemplate, String baseUri) {
        this.restTemplate = restTemplate;
        this.baseUri = baseUri;
    }

    @Override
    public Optional<PiHumidityResponse> readHumidity() {

        try {
            var response = restTemplate.getForEntity(baseUri + "/soil-sensor", PiHumidityResponse.class);
            logger.info("success humidity reading");

            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody());
            }

        } catch (Exception ex) {
            logger.error("can't get humidity value - ", ex);
        }

        return Optional.empty();
    }

    @Override
    public Optional<PiTemperatureResponse> readTemperature() {

        try {

            var response = restTemplate.getForEntity(baseUri + "/temperature-sensor", PiTemperatureResponse.class);
            logger.info("success temperature reading");

            if (response.getStatusCode().is2xxSuccessful()) {

                return Optional.ofNullable(response.getBody());
            }
        } catch (Exception ex) {

            logger.error("can't read temperature value - ", ex);
        }

        return Optional.empty();
    }

    @Override
    public Optional<PiResponse> setWaterPump(WaterPumpState state) {

        try {
            var request = new WaterPumpRequest(state.getValue());
            var response = restTemplate.postForEntity(baseUri + "/water-pump", request, PiResponse.class);
            logger.info("success water pump post: {}", state);

            if (response.getStatusCode().is2xxSuccessful()) {

                return Optional.ofNullable(response.getBody());
            }
        } catch (Exception ex) {

            logger.error("can't read temperature value - ", ex);
        }

        return Optional.empty();
    }
}
