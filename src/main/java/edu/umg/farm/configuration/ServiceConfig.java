package edu.umg.farm.configuration;

import edu.umg.farm.dao.ReadEventDao;
import edu.umg.farm.pi.client.PiClient;
import edu.umg.farm.service.FarmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Value("${farm.control.humidity.threshold:10}")
    private long humidityThreshold;

    @Value("${farm.control.temperature.threshold:30}")
    private long temperatureThreshold;

    @Bean
    public FarmService farmService(PiClient piClient, ReadEventDao readEventDao) {

        return new FarmService(humidityThreshold, temperatureThreshold, piClient, readEventDao);
    }

}
