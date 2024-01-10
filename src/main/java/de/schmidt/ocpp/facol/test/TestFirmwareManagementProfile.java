package de.schmidt.ocpp.facol.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import de.schmidt.ocpp.facol.repository.ConnectorRepository;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.server.JsonServerImpl;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest;
import eu.chargetime.ocpp.model.firmware.UpdateFirmwareRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class TestFirmwareManagementProfile {

    @Autowired
    private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ConnectorRepository connectorRepo;


    //Requests
    public void testDiagnosticStatusReq(UUID sessionIndex, DiagnosticsStatusNotificationRequest request)
    {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("TestDiagnosticStatusNotificationReq.Req");

        if(request.getStatus() != null)
        {
            test.pass(request.toString());
        } else {
            test.fail(request.toString());
        }
    }

    public void testFirmwareStatusNotificationReq(UUID sessionIndex, FirmwareStatusNotificationRequest request)
    {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("TestFirmwareStatusNotification.Req");

        if(request.getStatus() != null)
        {
            test.pass(request.toString());
        } else {
            test.fail(request.toString());
        }
    }

    //Confirmation
    public void testUpdateFirmwareConfirmation(UUID sessionIndex) {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("TestUpdateFirmwareConfirmation.Conf");

        UpdateFirmwareRequest request = new UpdateFirmwareRequest("https://sbeams.systemsbiology.net/sample_data/Microarray/Affy_test_data.tar.gz", ZonedDateTime.now());

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation);
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail (new RuntimeException(e));
        }
        reporter.flush();
    }


    public void testGetDiagonisticsConfirmation(UUID sessionIndex) {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("TestGetDiagonisticsConfirmation.Conf");

        GetDiagnosticsRequest request = new GetDiagnosticsRequest("https://nextcloud.patrick-schmidt.me/index.php/s/xxpTgJfq3W9mRaf");

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation);
            });
        }  catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
    }

}
