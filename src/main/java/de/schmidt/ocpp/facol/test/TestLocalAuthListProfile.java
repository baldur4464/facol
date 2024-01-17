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
import eu.chargetime.ocpp.model.core.AuthorizationStatus;
import eu.chargetime.ocpp.model.core.IdTagInfo;
import eu.chargetime.ocpp.model.localauthlist.AuthorizationData;
import eu.chargetime.ocpp.model.localauthlist.GetLocalListVersionRequest;
import eu.chargetime.ocpp.model.localauthlist.SendLocalListRequest;
import eu.chargetime.ocpp.model.localauthlist.UpdateType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class TestLocalAuthListProfile {

    @Autowired
    private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ConnectorRepository connectorRepo;

    public void testSendLocalList(UUID sessionIndex) {

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test SendLocalList.Conf");

        SendLocalListRequest request = new SendLocalListRequest(1, UpdateType.Full);


        AuthorizationData data1 = new AuthorizationData("zero");
        IdTagInfo tagData1 = new IdTagInfo(AuthorizationStatus.Accepted);
        tagData1.setParentIdTag("");
        tagData1.setExpiryDate(ZonedDateTime.now().plusYears(1));
        data1.setIdTagInfo(tagData1);
        AuthorizationData data2 = new AuthorizationData("one");
        IdTagInfo tagData2 = new IdTagInfo(AuthorizationStatus.Accepted);
        tagData2.setParentIdTag("");
        tagData2.setExpiryDate(ZonedDateTime.now().plusYears(1));
        data2.setIdTagInfo(tagData2);

        AuthorizationData[] authArray = new AuthorizationData[]{data1, data2};
        request.setLocalAuthorizationList(authArray);

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
        reporter.flush();
    }

    /* Dieser Test funktioniert nur mit Java-OCA-OCPP 1.2
    public void testSendLocalListClearList(UUID sessionIndex) {

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test SendLocalList.Conf");

        SendLocalListRequest request = new SendLocalListRequest(0, UpdateType.Full);

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());

                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
        reporter.flush();
    }

     */
    public void testGetLocalListVersion(UUID sessionIndex) {

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test GetLocalListVersion.Conf");

        GetLocalListVersionRequest request = new GetLocalListVersionRequest();

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) -> {
                System.out.println(confirmation.toString());
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
        reporter.flush();
    }
}
