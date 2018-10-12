package edu.umg.farm.pi.client;

import edu.umg.farm.pi.model.WaterPumpState;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = { PiConfig.class })
public class RestPiClientTest {

    private String baseUri = "http://pi-service";
    private MockRestServiceServer serviceServer;

    private RestTemplate restTemplate;

    private PiClient piClient;

    @Before
    public void setup() {

        restTemplate = new RestTemplate();
        serviceServer = MockRestServiceServer.bindTo(restTemplate)
                .build();

        piClient = new RestPiClient(restTemplate, baseUri);
    }

    @Test
    public void readHumidityTest() {

        serviceServer.expect(ExpectedCount.once(), requestTo(baseUri + "/soil-sensor"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getHumidityResponse(), MediaType.APPLICATION_JSON));

        var response = piClient.readHumidity();
        assertTrue(response.isPresent());
        serviceServer.verify();
    }

    @Test
    public void readTemperatureTest() {

        serviceServer.expect(ExpectedCount.once(), requestTo(baseUri + "/temperature-sensor"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getTemperatureResponse(), MediaType.APPLICATION_JSON));

        var response = piClient.readTemperature();

        assertTrue(response.isPresent());
        serviceServer.verify();
    }

    @Test
    public void turnOnWaterPump() {

        serviceServer.expect(ExpectedCount.once(), requestTo(baseUri + "/water-pump"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"action\":1}"))
                .andRespond(withSuccess(getWaterPumpResponse(), MediaType.APPLICATION_JSON));

        var response = piClient.setWaterPump(WaterPumpState.ON);

        assertTrue(response.isPresent());
        serviceServer.verify();
    }

    @Test
    public void turnOffWaterPump() {

        serviceServer.expect(ExpectedCount.once(), requestTo(baseUri + "/water-pump"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"action\":0}"))
                .andRespond(withSuccess(getWaterPumpResponse(), MediaType.APPLICATION_JSON));

        var response = piClient.setWaterPump(WaterPumpState.OFF);

        assertTrue(response.isPresent());
        serviceServer.verify();
    }

    // ------------------------------------------------------------------------

    private String getHumidityResponse() {

        return "{" +
                "  \"humidity\": 20.00," +
                "  \"requestId\": \"12123123123123\"" +
                "}";
    }

    private String getTemperatureResponse() {

        return "{" +
                "  \"humidity\": 20.00," +
                "  \"requestId\": \"12123123123123\"" +
                "}";
    }

    private String getWaterPumpResponse() {

        return "{" +
                "  \"requestId\": \"12123123123123\"" +
                "}";
    }

}
