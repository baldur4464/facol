package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestCoreProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestCoreProfileConfig {
    @Bean
    public TestCoreProfile getTestCoreProfile() {
        return new TestCoreProfile();
    }
}
