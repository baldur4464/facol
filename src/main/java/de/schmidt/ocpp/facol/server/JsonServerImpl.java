package de.schmidt.ocpp.facol.server;

import eu.chargetime.ocpp.*;
import de.schmidt.ocpp.facol.config.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


@Slf4j
@Component
@DependsOn({"chargepointConfig","applicationConfig", "jsonServerConfig", "serverCoreProfileConfig", "serverEventConfig"})
@AllArgsConstructor
public class JsonServerImpl {

    private final ServerEvents serverEvents;
    private final JSONServer server;
    private final ApplicationConfiguration applicationConfiguration;

    @PostConstruct
    public void startServer() throws Exception {
        server.open(applicationConfiguration.getServerAddress(), applicationConfiguration.getServerPort(), serverEvents);
    }
}

