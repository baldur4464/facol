package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestSmartChargingProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestSmartChargingProfileConfig {
    @Bean
    public TestSmartChargingProfile getTestSmartChargingProfile() {
        return new TestSmartChargingProfile();
    }
}
