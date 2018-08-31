package edu.umg.farm.configuration;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.service.FarmService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FarmService farmService(ArduinoClient arduinoClient) {

        return new FarmService(arduinoClient);
    }

}
