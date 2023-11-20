package de.schmidt.ocpp.facol;

import de.schmidt.ocpp.facol.model.Session;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.test.TestCoreProfile;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class FacolApplication
{

    @Autowired
    private TestCoreProfile tTestCore;
    private static TestCoreProfile testCore;

    @Autowired
    private SessionRepository tSessionRepo;
    private static SessionRepository sessionRepo;

    @Autowired
    private ProfileTestController tTestController;

    private static ProfileTestController testController;



    public static void main(String[] args)
    {
        SpringApplication.run(FacolApplication.class, args);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int auswahl = -1;

        while(true) {

            List<Session> sessions = sessionRepo.findAll();

            if(sessions != null  && sessions.size() != 0) {
                System.out.println("Wähle ein verfügbare Session zum Testen: ");
                for (int i = 0; i < sessions.size(); i++) {
                    System.out.println("[" + i + "]" + " " + sessions.get(i).getSessionUuid().toString() + " : " + sessions.get(i).getChargepoint().getChargepointId());
                }
                System.out.println("Eingabe: ");
                try{
                    auswahl = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    auswahl = -1;
                    e.printStackTrace();
                }

                if(auswahl >= 0) {
                    Session session = sessions.get(auswahl);
                    ProfileTest profileTest = testController.getProfileTestBySessionUuid(UUID.fromString(session.getSessionUuid()));
                    testCore.testRemoteStartTransactionConf(UUID.fromString(session.getSessionUuid()), profileTest.isCanTest());
                    //testCore.testRemoteStopTransactionConf();
                    profileTest.getReporter().flush();
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    public void init() {
        FacolApplication.testCore = tTestCore;
        FacolApplication.sessionRepo = tSessionRepo;
        FacolApplication.testController = tTestController;
    }

}
