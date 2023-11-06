package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.Session;
import de.schmidt.ocpp.facol.repository.ChargepointRepository;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@Getter
@Slf4j
public class ServerEventConfig {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ChargepointRepository chargepointRepository;

    @Bean
    public ServerEvents createServerCoreImpl() {
        return getNewServerEventsImpl();
    }

    private ServerEvents getNewServerEventsImpl() {
        return new ServerEvents() {

            @Override
            public void authenticateSession(SessionInformation sessionInformation, String s, byte[] bytes) throws AuthenticationException {
                System.out.println("Authenicate Session: " + sessionInformation.getIdentifier() + "String s? = " + s + "Bytes: " + bytes);
            }

            @Override
            public void newSession(UUID sessionIndex, SessionInformation information) {

                String [] split = information.getIdentifier().split("/openocpp/");
                System.out.println("split = " + split[1]);
                String chargePointIdentifier = split[1];

                if(chargepointRepository.existsById(chargePointIdentifier)) {
                    Optional<Chargepoint> chargepointOpt = chargepointRepository.findById(chargePointIdentifier);
                    Chargepoint chargepoint = chargepointOpt.get();

                    Session session = Session.builder()
                            .sessionUUID(sessionIndex)
                            .chargepoint(chargepoint)
                            .build();

                    sessionRepository.save(session);
                }

                // sessionIndex is used to send messages.
                System.out.println("New session " + sessionIndex + ": " + information.getIdentifier());
            }

            @Override
            public void lostSession(UUID sessionIndex) {

                System.out.println("Session " + sessionIndex + " lost connection");
            }
        };
    }
}
