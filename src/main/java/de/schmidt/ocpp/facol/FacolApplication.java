package de.schmidt.ocpp.facol;

import de.schmidt.ocpp.facol.model.Session;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.test.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class FacolApplication {

    private static TestCoreProfile testCore;

    private static TestRemoteTriggerProfile testRemote;

    private static SessionRepository sessionRepo;

    private static TestReservationProfile testReservation;

    private static TestLocalAuthListProfile testLocalAuthList;

    private static TestSmartChargingProfile testSmartCharging;

    private static TestFirmwareManagementProfile testFirmwareManagement;

    @Autowired
    private TestCoreProfile tTestCore;

    @Autowired
    private TestRemoteTriggerProfile tTestRemote;

    @Autowired
    private SessionRepository tSessionRepo;

    @Autowired
    private TestReservationProfile tTestReservation;

    @Autowired
    private TestLocalAuthListProfile tTestAuthList;

    @Autowired
    private TestSmartChargingProfile tTestSmartCharging;

    @Autowired
    private TestFirmwareManagementProfile tTestFirmwareUpdate;

    public static void main(String[] args) {

        SpringApplication.run(FacolApplication.class, args);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int auswahl = -1;

        while (true) {


            List<Session> sessions = sessionRepo.findAll();

            if (sessions.size() != 0) {
                System.out.println("Wähle ein verfügbare Session zum Testen: ");
                for (int i = 0; i < sessions.size(); i++) {
                    System.out.println("[" + i + "]" + " " + sessions.get(i).getSessionUuid() + " : " + sessions.get(i).getChargepoint().getChargepointId());
                }
                System.out.println("Eingabe: ");
                try {
                    auswahl = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    auswahl = -1;
                    e.printStackTrace();
                }

                if (auswahl >= 0 && auswahl >= sessions.size() - 1) {
                    Session session = sessions.get(auswahl);


                    testCore.testRemoteStartTransactionConf(UUID.fromString(session.getSessionUuid()));
                    sleep(10000);
                    testCore.testRemoteStopTransactionConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testCore.testChangeAvailabilityConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testCore.testChangeConfigurationConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testCore.testGetConfigurationConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testCore.testClearCacheConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testCore.testUnlockConnectorConf(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);

                    //Remote Trigger
                    testRemote.testTriggerBootnotification(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testRemote.testTriggerHeartbeat(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testRemote.testTriggerMeterValues(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testRemote.testTriggerStatusNotification(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testRemote.testTriggerFirmwareStatusNotification(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testRemote.testTriggerDiagnosticsStatusNotification(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);

                    //Local Auth List Management
                    testLocalAuthList.testSendLocalList(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testLocalAuthList.testGetLocalListVersion(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    //testLocalAuthList.testSendLocalListClearList(UUID.fromString(session.getSessionUuid()));
                    //sleep(1000);
                    testLocalAuthList.testGetLocalListVersion(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);


                    //Reservation
                    testReservation.testReservationNow(UUID.fromString(session.getSessionUuid()));
                    sleep(5000);
                    testReservation.testCancelReservation(UUID.fromString(session.getSessionUuid()));

                    //Smart Charging
                    testSmartCharging.testSetChargingProfile(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testSmartCharging.testGetCompositeSchedule(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testSmartCharging.testClearChargingProfile(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);

                    //Test Firmware Managment
                    testFirmwareManagement.testUpdateFirmwareConfirmation(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                    testFirmwareManagement.testGetDiagonisticsConfirmation(UUID.fromString(session.getSessionUuid()));
                    sleep(1000);
                }
            }
            sleep(5000);
        }
    }

    @PostConstruct
    public void init() {
        FacolApplication.testCore = tTestCore;
        FacolApplication.sessionRepo = tSessionRepo;
        FacolApplication.testRemote = tTestRemote;
        FacolApplication.testReservation = tTestReservation;
        FacolApplication.testLocalAuthList = tTestAuthList;
        FacolApplication.testSmartCharging = tTestSmartCharging;
        FacolApplication.testFirmwareManagement = tTestFirmwareUpdate;
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
