package edu.umg.farm.controllers;

import edu.umg.farm.BaseIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class HomeControllerIT extends BaseIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void test() throws Exception {

        mockMvc().perform(get("/"))
                .andExpect(status().isOk());

    }

    private MockMvc mockMvc() {

        return webAppContextSetup(webApplicationContext)
                .build();

    }
}
