package de.schmidt.ocpp.facol.test;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import de.schmidt.ocpp.facol.repository.ConnectorRepository;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequestType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
public class TestRemoteTriggerProfile {

    @Autowired
    private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ConnectorRepository connectorRepo;

    //Trigger Messages

    //Bootnotification
    public void testTriggerBootnotification(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test Bootnotification Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.BootNotification);

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
        reporter.flush();
    }

    public void testTriggerDiagnosticsStatusNotification(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test DiagonsticsStatusNotification Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.DiagnosticsStatusNotification);

        test.skip("Test is not implemented");

        /*
        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }

         */
        reporter.flush();
    }

    public void testTriggerFirmwareStatusNotification(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test FirmwareStatusNotification Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.BootNotification);

        test.skip("Test is not implemented");

        /*
        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
         */
        reporter.flush();
    }

    public void testTriggerHeartbeat(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test Heartbeat Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.Heartbeat);


        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
        reporter.flush();
    }

    public void testTriggerMeterValues(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test MeterValues Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.MeterValues);


        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
        reporter.flush();
    }

    public void testTriggerStatusNotification(UUID sessionIndex){
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test StatusNotification Triggermessage.conf");

        TriggerMessageRequest request = new TriggerMessageRequest(TriggerMessageRequestType.StatusNotification);


        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
        reporter.flush();
    }
}
