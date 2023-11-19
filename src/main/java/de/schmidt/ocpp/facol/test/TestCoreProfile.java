package de.schmidt.ocpp.facol.test;

import com.aventstack.extentreports.ExtentReports;
import com.google.gson.JsonObject;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import eu.chargetime.ocpp.model.core.BootNotificationRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
public class TestCoreProfile {

    @Autowired
   private ProfileTestController testController;

    /*
     * Hier werden eingehende Nachrichten getestet.
     */

    public static void TestAuthorizeReq() {
        //Test Authorize Request
        /*
         * Required:
         * idTag (String)
         */

    }

    public void TestBootNotificationReq(UUID sessionIndex, BootNotificationRequest request, boolean accepted) {
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
        reporter.flush();
    }

    public void TestDataTransfer() {
        //Test DataTransfer Request
    }

    public void TestHeartbeatReq () {
        //Test TriggerMessage Request
    }

    public void TestMeterValues() {
        //Test MeterValues Request
    }

    public void TestStartTransactionReq() {
        //Test StartTransaction Request
    }

    public void TestStopTransactionReq() {
        //Test StopTransaction Request
    }

    public void TestStatusNotificationReq() {
        //Test StatusNotificationRequest
    }

    /*
     * Hier werden ausgehende Nachrichten getestet.
     */

    public void TestRemoteStartTransactionReq() {
        //Test RemoteStartTransaction Conf
    }

    public void TestRemoteStopTransactionReq() {
        //Test RemoteStopTransaction Conf
    }
}
