package de.schmidt.ocpp.facol.config;


import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ProfileTestControllerConfig {

    @Bean
    public ProfileTestController getProfileTestController () {
        return ProfileTestController.builder()
                .profileTests(new ArrayList<>())
                .build();
    }
}
