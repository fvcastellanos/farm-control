package edu.umg.farm.configuration;

import edu.umg.farm.arduino.ArduinoClient;
import edu.umg.farm.arduino.ArduinoDummyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArduinoConfig {

    @Bean
    public ArduinoClient arduinoDummyClient() {
        return new ArduinoDummyClient();
    }

}
