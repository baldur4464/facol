package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestReservationProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestReservationProfileConfig {
    @Bean
    public TestReservationProfile getTestReservationProfile() {
        return new TestReservationProfile();
    }
}
