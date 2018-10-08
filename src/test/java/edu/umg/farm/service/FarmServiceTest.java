package edu.umg.farm.service;

import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.ReadEvent;
import edu.umg.farm.pi.client.PiClient;
import edu.umg.farm.pi.model.PiHumidityResponse;
import edu.umg.farm.pi.model.PiResponse;
import edu.umg.farm.pi.model.PiTemperatureResponse;
import edu.umg.farm.pi.model.WaterPumpState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FarmServiceTest {

    private final static double HUMIDITY_THRESHOLD = 10;
    private final static double TEMPERATURE_THRESHOLD = 15;

    @Mock
    private ReadEventDao readEventDao;

    @Mock
    private PiClient piClient;

    private FarmService farmService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        farmService = new FarmService(HUMIDITY_THRESHOLD, TEMPERATURE_THRESHOLD, piClient, readEventDao);
    }

    @Test
    public void happyPathTest() {

        expectSensorRead(11, 14);
        expectSuccessEventPublished();
        expectPumpActivation(false);

        farmService.checkSoil();

        verify(piClient).readHumidity();
        verify(piClient).readTemperature();
        verify(piClient).setWaterPump(WaterPumpState.OFF);
        verify(readEventDao).saveReadEvent(any(ReadEvent.class));

        verifyNoMoreInteractions(piClient, readEventDao);
    }

    @Test
    public void pumpActivationTest() {

        expectSensorRead(8, 10);
        expectSuccessEventPublished();
        expectPumpActivation(true);

        farmService.checkSoil();

        verify(piClient).readHumidity();
        verify(piClient).readTemperature();
        verify(piClient).setWaterPump(WaterPumpState.ON);
        verify(readEventDao).saveReadEvent(any(ReadEvent.class));

        verifyNoMoreInteractions(piClient, readEventDao);
    }

    private void expectSensorRead(double humidityValue, double temperatureValue) {

        var humidityResponse = new PiHumidityResponse();
        humidityResponse.setHumidity(humidityValue);

        doReturn(Optional.of(humidityResponse))
                .when(piClient).readHumidity();

        var temperatureResponse = new PiTemperatureResponse();
        temperatureResponse.setTemperature(temperatureValue);

        doReturn(Optional.of(temperatureResponse))
                .when(piClient).readTemperature();
    }

    private void expectSuccessEventPublished() {

        var readEvent = ReadEvent.builder()
                .id(10)
                .build();

        doReturn(readEvent)
                .when(readEventDao).saveReadEvent(any(ReadEvent.class));
    }

    private void expectPumpActivation(boolean activation) {

        var activationResponse = new PiResponse();
        activationResponse.setRequestId("blah...");

        var responseHolder = Optional.of(activationResponse);

        if (activation) {
            doReturn(responseHolder)
                    .when(piClient).setWaterPump(WaterPumpState.ON);

            return;
        }

        doReturn(responseHolder)
                .when(piClient).setWaterPump(WaterPumpState.OFF);
    }

}
