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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");

        ChargingSchedule chargingSchedule = new ChargingSchedule(ChargingRateUnitType.A, periodArray);
        chargingSchedule.setStartSchedule(ZonedDateTime.parse("2024-01-01 00:00:00 am +02:00", df));

        ChargingProfile chargingProfile = new ChargingProfile(
                1,
                0,
                ChargingProfilePurposeType.ChargePointMaxProfile,
                ChargingProfileKindType.Recurring,
                chargingSchedule
                );
        chargingProfile.setRecurrencyKind(RecurrencyKindType.Daily);

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

        ChargingSchedulePeriod period1 = new ChargingSchedulePeriod(0, 16d);
        ChargingSchedulePeriod period2 = new ChargingSchedulePeriod(28800, 0d);
        ChargingSchedulePeriod period3 = new ChargingSchedulePeriod(57600, 16d);
        ChargingSchedulePeriod period4 = new ChargingSchedulePeriod(86400, 0d);
        ChargingSchedulePeriod[] periodArray = new ChargingSchedulePeriod[4];
        periodArray[0] = period1;
        periodArray[1] = period2;
        periodArray[2] = period3;
        periodArray[3] = period4;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");

        ChargingSchedule chargingSchedule = new ChargingSchedule(ChargingRateUnitType.A, periodArray);
        chargingSchedule.setStartSchedule(ZonedDateTime.parse("2024-01-01 00:00:00 am +02:00", df));

        ChargingProfile chargingProfile = new ChargingProfile(
                1,
                0,
                ChargingProfilePurposeType.ChargePointMaxProfile,
                ChargingProfileKindType.Recurring,
                chargingSchedule
        );
        chargingProfile.setRecurrencyKind(RecurrencyKindType.Daily);

        SetChargingProfileRequest chargeRequest = new SetChargingProfileRequest(0, chargingProfile);
        chargeRequest.setConnectorId(0);

        try {
            server.send(sessionIndex, chargeRequest);
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

        GetCompositeScheduleRequest request = new GetCompositeScheduleRequest(0, 86400);

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
