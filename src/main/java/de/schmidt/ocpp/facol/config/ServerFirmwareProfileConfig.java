package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.test.TestCoreProfile;
import de.schmidt.ocpp.facol.test.TestFirmwareManagementProfile;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementProfile;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;
@Configuration
public class ServerFirmwareProfileConfig {


    private TestFirmwareManagementProfile tester;

    //Setter Injection
    @Autowired
    public void setTester(@Lazy TestFirmwareManagementProfile tester){
        this.tester = tester;
    }

    @Bean
    public ServerFirmwareManagementEventHandler getFirmwareManagementEventHandler() {
        return new ServerFirmwareManagementEventHandler() {
            @Override
            public DiagnosticsStatusNotificationConfirmation handleDiagnosticsStatusNotificationRequest(UUID uuid, DiagnosticsStatusNotificationRequest request) {
                System.out.println(request);

                return new DiagnosticsStatusNotificationConfirmation();
            }

            @Override
            public FirmwareStatusNotificationConfirmation handleFirmwareStatusNotificationRequest(UUID uuid, FirmwareStatusNotificationRequest request) {
                System.out.println(request);


                return new FirmwareStatusNotificationConfirmation();
            }
        };
    }

    @Bean
    public ServerFirmwareManagementProfile createFirmwareManagement(ServerFirmwareManagementEventHandler serverFirmwareManagementEventHandler) {
        return new ServerFirmwareManagementProfile(serverFirmwareManagementEventHandler);
    }
}
