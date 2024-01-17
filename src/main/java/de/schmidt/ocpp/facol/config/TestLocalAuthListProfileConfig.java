package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestLocalAuthListProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestLocalAuthListProfileConfig {
    @Bean
    public TestLocalAuthListProfile getTestLocalAuthListProfile() {
        return new TestLocalAuthListProfile();
    }
}
