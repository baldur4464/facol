package de.schmidt.ocpp.facol.config;


import de.schmidt.ocpp.facol.test.TestRemoteTriggerProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRemoteTriggerProfileConfig {

    @Bean
    public TestRemoteTriggerProfile getTestRemoteTriggerProfile() {
        return new TestRemoteTriggerProfile();
    }
}
