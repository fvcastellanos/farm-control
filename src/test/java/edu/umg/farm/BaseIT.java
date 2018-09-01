package edu.umg.farm;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource({ "classpath:application.properties", "classpath:application-test.properties" })
public abstract class BaseIT {

}
