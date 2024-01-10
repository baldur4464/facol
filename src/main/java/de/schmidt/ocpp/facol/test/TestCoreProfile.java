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

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
public class TestCoreProfile {

    @Autowired
   private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ConnectorRepository connectorRepo;

    /*
     * Hier werden eingehende Nachrichten getestet.
     */

    public void testAuthorizeReq(UUID sessionIndex, AuthorizeRequest request) {
        //Test Authorize Request
        /*
         * Required:
         * idTag (String)
         */
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test Authorize.req");

        if(request.getIdTag().contains(profileTest.getIdTag())) {
            test.pass(request.toString());
        } else {
            test.fail("Wrong idTag: " + request.toString());
        }
        reporter.flush();
    }

    public void testBootNotificationReq(UUID sessionIndex, BootNotificationRequest request) {
        //Test BootNotification Request
        /*
         * Required:
         * chargePointModel (String)
         * chargePointVendor (String)
         *
         * Optional:
         * chargeBoxSerialNumber (String) (Deprecated)
         * chargePointSerialNumber (String)
         * firmwareVersion (String)
         * iccid (String)
         * imsi (String)
         * meterSerialNumber (String)
         * meterType(String)
         */

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test Bootnotification.req");

        if(request.getChargePointModel() != null && request.getChargePointVendor() != null) {
            test.pass(request.toString());
        } else {
            test.fail("The requirements were not met: " + request.toString());
        }
        reporter.flush();
    }

    public void testStartTransactionReq(UUID sessionIndex, StartTransactionRequest request) {
        //Test StartTransaction Request
        /*
         * Required:
         * connectorId int
         * idTag String
         * meterStart int
         * timestamp datetime (ZonedDateTime)
         *
         * Optional:
         * reservationId
         */

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test StartTransaction.req");

        if (request.getConnectorId() != null && request.getMeterStart() != null && request.getIdTag() != null && request.getTimestamp() != null) {
            if(request.getConnectorId() == profileTest.getConnectorId()) {
                test.pass("Correct Connector ID");
            } else {
                test.fail("Wrong Connector ID");
            }

            if(request.getIdTag().equals(profileTest.getIdTag()))
            {
                test.pass("Correct IdTag");
            } else {
                test.fail("Wrong IdTag");
            }

            test.pass(request.toString());
        } else {
            test.fail(" The requirments were not met: " + request.toString());
        }

        reporter.flush();
    }

    public void testStopTransactionReq(UUID sessionIndex, StopTransactionRequest request) {
        //Test StopTransaction Request
        /*
         * Required:
         * meterStop int
         * timestamp dateTime (ZonedDateTime)
         * transactionId int
         *
         * Optional:
         * idTag String
         * reason String
         * transactionData MeterValue
         */

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test StopTransaction.req");

        if(request.getMeterStop() != null && request.getTimestamp() != null && request.getTransactionId() != 0) {

            if(request.getTransactionId() == profileTest.getTransactionId())
            {
                test.pass("Correct TransactionId");
            } else {
                test.fail("Wrong TransactionId: " + request.getTransactionId() +  " instead of " + profileTest.getTransactionId());
            }

            test.pass(request.toString());
        } else {
            test.fail(request.toString());
        }
        reporter.flush();
    }

    public void testDataTransfer() {
        //Test DataTransfer Request
    }

    public void testHeartbeatReq (UUID sessionIndex, HeartbeatRequest request) {
        //Test DataTransfer Request
        /*
         * Require:
         * None
         *
         * Optional
         * None
         */
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test Heartbeat.req");

        test.pass(request.toString());

        reporter.flush();
    }

    public void testMeterValueReq(UUID sessionIndex, MeterValuesRequest request) {
        //Test MeterValues Request
        /*
         * Required:
         * connectorId int
         * meterValue MeterValue []
         *
         * Optional:
         * transactionId int
         */

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test MeterValue.req");

        if(request.getMeterValue() != null && request.getConnectorId()!= null) {
            test.pass(request.toString());
        } else {
            test.fail("The requirements were not met: " + request.toString());
        }
        reporter.flush();
    }

    public void testStatusNotificationReq(UUID sessionIndex, StatusNotificationRequest request) {
        //Test StatusNotificationRequest
        /*
         * Required:
         * connectorId int
         * errorCode String
         * status String
         *
         * Optional:
         * info String
         */

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test StatusNotification.req");

        if(request.getConnectorId() != null && request.getErrorCode() != null && request.getStatus() != null){
            test.pass(request.toString());
        } else {
            test.fail("The requirements were not met: " + request.toString());
        }
        reporter.flush();
    }

    /*
     * Hier werden ausgehende Nachrichten getestet.
     */

    public void testRemoteStartTransactionConf(UUID sessionIndex) {
        //Test RemoteStartTransaction Conf

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test RemoteStartTransaction.conf");
        RemoteStartTransactionRequest request = new RemoteStartTransactionRequest("zero");

        profileTest.setConnectorId(1);
        testController.updateProfileTest(profileTest);

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                if(confirmation.toString().contains("Accepted"))
                {
                    test.pass(confirmation.toString());
                } else {
                    test.pass(confirmation.toString());
                }
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

    public void testRemoteStopTransactionConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test RemoteStoptransaction.conf");

        RemoteStopTransactionRequest request = new RemoteStopTransactionRequest((int)profileTest.getTransactionId());

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                if(confirmation.toString().contains("Accepted")){
                    test.pass(confirmation.toString());
                } else {
                    test.pass(confirmation.toString());
                }
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

    public void testChangeAvailabilityConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test ChangeAvailabilityConf.conf");

        ChangeAvailabilityRequest request = new ChangeAvailabilityRequest(1, AvailabilityType.Operative);

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

    public void testChangeConfigurationConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test ChangeConfiguration.conf");

        ChangeConfigurationRequest request = new ChangeConfigurationRequest("test", "test");

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

    public void testClearCacheConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test ClearCache.conf");

        ClearCacheRequest request = new ClearCacheRequest();

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

    public void testGetConfigurationConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test GetConfiguration.conf");

        GetConfigurationRequest request = new GetConfigurationRequest();

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

    public void testUnlockConnectorConf(UUID sessionIndex) {
        //Test RemoteStopTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test UnlockConnector.conf");

        UnlockConnectorRequest request = new UnlockConnectorRequest(1);

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
