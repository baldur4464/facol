package de.schmidt.ocpp.facol.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.JsonObject;
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

    /*
     * Hier werden eingehende Nachrichten getestet.
     */

    public static void testAuthorizeReq(UUID sessionIndex, AuthorizeRequest request, boolean allowTest) {
        //Test Authorize Request
        /*
         * Required:
         * idTag (String)
         */

    }

    public void testBootNotificationReq(UUID sessionIndex, BootNotificationRequest request, boolean allowTest) {
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

        JsonObject object = new JsonObject();
        object.addProperty("chargePointModel", request.getChargePointModel());
        object.addProperty("chargePointVendor", request.getChargePointVendor());
        object.addProperty("chargeBoxSerialNumber", request.getChargeBoxSerialNumber());
        object.addProperty("chargePointSerialNumber", request.getChargePointSerialNumber());
        object.addProperty("firmwareVersion", request.getFirmwareVersion());
        object.addProperty("iccid", request.getIccid());
        object.addProperty("imsi", request.getImsi());
        object.addProperty("meterSerialNumber", request.getMeterSerialNumber());
        object.addProperty("meterType", request.getMeterType());

        System.out.println(object.toString());

        ProfileTest test = testController.getProfileTestBySessionUuid(sessionIndex);

        ExtentReports reporter = test.getReporter();

        reporter.createTest("Bootnotification").pass("working");
    }

    public void testStartTransactionReq() {

    }

    public void testStopTransactionReq() {

    }

    public void testDataTransfer() {
        //Test DataTransfer Request
    }

    public void testHeartbeatReq () {
        //Test TriggerMessage Request
    }

    public void testMeterValues() {
        //Test MeterValues Request
    }

    public void testStartTransactionReq(UUID sessionIndex, StartTransactionRequest request, boolean allowTest) {
        //Test StartTransaction Request
    }

    public void testStatusNotificationReq() {
        //Test StatusNotificationRequest
    }

    /*
     * Hier werden ausgehende Nachrichten getestet.
     */

    public void testRemoteStartTransactionConf(UUID sessionIndex, boolean allowTest) {
        //Test RemoteStartTransaction Conf
        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test RemoteStartTransaction");
        RemoteStartTransactionRequest request = new RemoteStartTransactionRequest("zero");
        try {
            server.send(sessionIndex, request).whenComplete(((confirmation, throwable) -> {
                if(confirmation.toString().contains("Accepted"));
                test.pass("RemoteStartTransaction passed");
            }));
        } catch (OccurenceConstraintException e) {
            test.fail(new RuntimeException(e));
        } catch (UnsupportedFeatureException e) {
            test.fail( new RuntimeException(e));
        } catch (NotConnectedException e) {
            test.fail(new RuntimeException(e));
        }
        profileTest.setRemoteStopTransactionTested(true);
    }

    public void testRemoteStopTransactionConf() {
        //Test RemoteStopTransaction Conf
    }
}
