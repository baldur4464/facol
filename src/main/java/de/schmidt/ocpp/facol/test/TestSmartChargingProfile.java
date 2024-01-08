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
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileRequest;
import eu.chargetime.ocpp.model.smartcharging.GetCompositeScheduleRequest;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class TestSmartChargingProfile {

    @Autowired
    private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ConnectorRepository connectorRepo;

    public void testSetChargingProfile(UUID sessionIndex) {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test SetChargingProfile.Conf");

        ChargingSchedulePeriod period1 = new ChargingSchedulePeriod(0, 30d);
        ChargingSchedulePeriod[] periodArray = new ChargingSchedulePeriod[1];
        periodArray[0] = period1;

        ChargingProfile chargingProfile = new ChargingProfile(
                1,
                0,
                ChargingProfilePurposeType.ChargePointMaxProfile,
                ChargingProfileKindType.Absolute,
                new ChargingSchedule(ChargingRateUnitType.W, periodArray));

        SetChargingProfileRequest request = new SetChargingProfileRequest(0, chargingProfile);

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

    public void testGetCompositeSchedule(UUID sessionIndex) {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test GetCompositeSchedule.Conf");

        ChargingSchedulePeriod period1 = new ChargingSchedulePeriod(4, 30d);
        ChargingSchedulePeriod[] periodArray = new ChargingSchedulePeriod[1];
        periodArray[0] = period1;

        ChargingProfile chargingProfile = new ChargingProfile(
                1,
                0,
                ChargingProfilePurposeType.ChargePointMaxProfile,
                ChargingProfileKindType.Absolute,
                new ChargingSchedule(ChargingRateUnitType.W, periodArray));

        RemoteStartTransactionRequest chargingRequest = new RemoteStartTransactionRequest("zero");
        chargingRequest.setConnectorId(1);
        chargingRequest.setChargingProfile(chargingProfile);

        try {
            server.send(sessionIndex, chargingRequest);
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        GetCompositeScheduleRequest request = new GetCompositeScheduleRequest(1, 0);

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation);
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }

        RemoteStopTransactionRequest stopRequest = new RemoteStopTransactionRequest((int) profileTest.getTransactionId());

        try {
            server.send(sessionIndex, stopRequest);
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail(new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
    }

    public void testClearChargingProfile(UUID sessionIndex) {
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test ClearChargineProfile.Conf");

        ClearChargingProfileRequest request = new ClearChargingProfileRequest();

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation);
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
