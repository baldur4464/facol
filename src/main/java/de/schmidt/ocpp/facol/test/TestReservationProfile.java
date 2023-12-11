package de.schmidt.ocpp.facol.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import de.schmidt.ocpp.facol.model.Connector;
import de.schmidt.ocpp.facol.model.Reservation;
import de.schmidt.ocpp.facol.model.Transaction;
import de.schmidt.ocpp.facol.repository.ConnectorRepository;
import de.schmidt.ocpp.facol.repository.ReservationRepository;
import de.schmidt.ocpp.facol.repository.SessionRepository;
import de.schmidt.ocpp.facol.test.controller.ProfileTestController;
import de.schmidt.ocpp.facol.test.model.ProfileTest;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.core.RemoteStartTransactionRequest;
import eu.chargetime.ocpp.model.core.RemoteStopTransactionRequest;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
import eu.chargetime.ocpp.model.reservation.ReserveNowRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;


public class TestReservationProfile {

    @Autowired
    private ProfileTestController testController;

    @Autowired
    private JSONServer server;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    private ConnectorRepository connectorRepo;

    //Test Reservation
    public void testReservationNow (UUID sessionIndex) {

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test ReserveNow.conf");

        Optional<Connector> connectorOpt = connectorRepo.findById(2l);


        if(!connectorOpt.isPresent())
            return;

        Connector connector = connectorOpt.get();

        Reservation reservation = Reservation.builder()
                .idTag("zero")
                .expiryDate(ZonedDateTime.now().plusHours(2))
                .connector(connector)
                .build();
        reservation = reservationRepo.save(reservation);

        ReserveNowRequest request = new ReserveNowRequest(
                reservation.getConnector().getConnectorId().intValue(),
                reservation.getExpiryDate(),
                reservation.getIdTag(),
                reservation.getReservationId().intValue());

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) ->
            {
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        RemoteStartTransactionRequest startRequest = new RemoteStartTransactionRequest(reservation.getIdTag());
        startRequest.setConnectorId(reservation.getConnector().getConnectorId().intValue());
        startRequest.setIdTag(reservation.getIdTag());

        try {
            server.send(sessionIndex, startRequest).whenComplete((confirmation, throwable) -> {
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        RemoteStopTransactionRequest stopRequest = new RemoteStopTransactionRequest((int) profileTest.getTransactionId());

        try {
            server.send(sessionIndex, stopRequest).whenComplete((confirmation, throwable) -> {
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testCancelReservation(UUID sessionIndex) {

        ProfileTest profileTest = testController.getProfileTestBySessionUuid(sessionIndex);
        ExtentReports reporter = profileTest.getReporter();
        ExtentTest test = reporter.createTest("Test CancelReservation.conf");

        Optional<Connector> connectorOpt = connectorRepo.findById(2l);


        if(!connectorOpt.isPresent())
            return;

        Connector connector = connectorOpt.get();

        Reservation reservation = Reservation.builder()
                .idTag("zero")
                .expiryDate(ZonedDateTime.now().plusHours(2))
                .connector(connector)
                .build();
        reservation = reservationRepo.save(reservation);

        ReserveNowRequest request = new ReserveNowRequest(
                reservation.getConnector().getConnectorId().intValue(),
                reservation.getExpiryDate(),
                reservation.getIdTag(),
                reservation.getReservationId().intValue());

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) ->
            {
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CancelReservationRequest cancelRequest = new CancelReservationRequest(reservation.getReservationId().intValue());

        try {
            server.send(sessionIndex, cancelRequest).whenComplete((confirmation, throwable) -> {
                test.pass(confirmation.toString());
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }

}
