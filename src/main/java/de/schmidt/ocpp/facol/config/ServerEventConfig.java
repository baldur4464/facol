package de.schmidt.ocpp.facol.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.Session;
import de.schmidt.ocpp.facol.repository.ChargepointRepository;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Configuration ("serverEventConfig")
@Getter
@Slf4j
public class ServerEventConfig {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ChargepointRepository chargepointRepository;

    @Autowired
    private ProfileTestController profileTestController;

    @Bean
    public ServerEvents createServerCoreImpl() {
        return getNewServerEventsImpl();
    }

    private ServerEvents getNewServerEventsImpl() {
        return new ServerEvents() {

            @Override
            public void authenticateSession(SessionInformation sessionInformation, String s, byte[] bytes) throws AuthenticationException {
                System.out.println("Authenicate Session: " + sessionInformation.getIdentifier() + " String s? = " + s + "Bytes: " + bytes.toString());
            }

            @Override
            public void newSession(UUID sessionIndex, SessionInformation information) {

                String [] split = information.getIdentifier().split("/ocpp/");
                String chargePointIdentifier = split[1];

                if(chargepointRepository.existsById(chargePointIdentifier)) {
                    Optional<Chargepoint> chargepointOpt = chargepointRepository.findById(chargePointIdentifier);
                    Chargepoint chargepoint = chargepointOpt.get();

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu_MM_dd_HHmmss");

                    ExtentSparkReporter spark = new ExtentSparkReporter("reports/report_" + dtf.format(LocalDateTime.now())  +  ".html");
                    ExtentReports extent = new ExtentReports();
                    extent.attachReporter(spark);

                    ProfileTest newProfileTest = new ProfileTest(extent, sessionIndex, false);
                    profileTestController.addProfileTest(newProfileTest);

                    Session session = Session.builder()
                            .sessionUuid(sessionIndex.toString())
                            .chargepoint(chargepoint)
                            .build();

                    System.out.println("session = " + session + " wurde zur Datenbank hinzugefügt");
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
