package edu.umg.farm.configuration;

import edu.umg.farm.pi.client.DummyPiClient;
import edu.umg.farm.pi.client.PiClient;
import edu.umg.farm.pi.client.RestPiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PiConfig {

    @Value("${farm.control.pi.url:http://192.168.0.220:8080}")
    private String baseUri;

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

    @Bean
    public PiClient restPiClient(RestTemplate restTemplate) {
        return new RestPiClient(restTemplate, baseUri);
    }

    @Bean
    @Primary
    @ConditionalOnProperty("farm.control.dummy.client")
    public PiClient dummyPiClient() {
        return new DummyPiClient();
    }
}
