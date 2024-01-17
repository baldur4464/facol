package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestFirmwareManagementProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestFirmwareMangementProfileConfig {
    @Bean
    public TestFirmwareManagementProfile getTestFirmwareManagementProfile() {
        return new TestFirmwareManagementProfile();
    }
}
