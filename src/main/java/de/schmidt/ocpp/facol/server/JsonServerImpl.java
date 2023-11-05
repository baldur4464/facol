package de.schmidt.ocpp.facol.server;

import de.schmidt.ocpp.facol.controller.OCPPMessages;


import eu.chargetime.ocpp.*;
import de.schmidt.ocpp.facol.config.ApplicationConfiguration;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


@Slf4j
@Component
@AllArgsConstructor
public class JsonServerImpl {

    private final ServerEvents serverEvents;
    private final JSONServer server;
    private final ApplicationConfiguration applicationConfiguration;
    private ServerCoreProfile profile;


    @PostConstruct
    public void startServer() throws Exception {
        OCPPMessages messageController;

        server.open(applicationConfiguration.getServerAddress(), applicationConfiguration.getServerPort(), serverEvents);
        messageController = OCPPMessages.getInstance();
        messageController.setProfile(profile);
        messageController.setServer(server);
    }
}
