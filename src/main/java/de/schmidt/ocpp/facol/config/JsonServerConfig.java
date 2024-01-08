package de.schmidt.ocpp.facol.config;

import eu.chargetime.ocpp.JSONConfiguration;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.feature.profile.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("jsonServerConfig")
@Slf4j
public class JsonServerConfig {

    @Bean
    public JSONServer jsonServer(ServerCoreProfile core) {

        JSONServer server = new JSONServer(core);
        server.addFeatureProfile(new ServerRemoteTriggerProfile());
        server.addFeatureProfile(new ServerReservationProfile());
        server.addFeatureProfile(new ServerLocalAuthListProfile());
        server.addFeatureProfile(new ServerSmartChargingProfile());

        return server;
    }

}