package edu.umg.farm.service;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.arduino.model.SensorRead;
import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.dao.model.ReadEvent;
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
    private final static double TEMPERATURE_THRESHOLD = 10;

    @Mock
    private ReadEventDao readEventDao;

    @Mock
    private ArduinoClient arduinoClient;

    private FarmService farmService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        farmService = new FarmService(HUMIDITY_THRESHOLD, TEMPERATURE_THRESHOLD, arduinoClient, readEventDao);
    }

    @Test
    public void happyPathTest() {

        expectSensorRead(11, 14);
        expectSuccessEventPublished();

        farmService.checkSoil();

        verify(arduinoClient).readHumiditySensor();
        verify(readEventDao).saveReadEvent(any(ReadEvent.class));

        verifyNoMoreInteractions(arduinoClient, readEventDao);
    }

    private void expectSensorRead(double humidityValue, double temperatureValue) {

        var sensorRead = SensorRead.builder()
                .humidityValue(humidityValue)
                .temperatureValue(temperatureValue)
                .build();

        doReturn(Optional.of(sensorRead))
                .when(arduinoClient).readHumiditySensor();
    }

    private void expectSuccessEventPublished() {

        var readEvent = ReadEvent.builder()
                .id(10)
                .build();

        doReturn(readEvent)
                .when(readEventDao).saveReadEvent(any(ReadEvent.class));
    }

}
