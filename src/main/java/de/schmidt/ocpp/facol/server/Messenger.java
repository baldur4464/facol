package de.schmidt.ocpp.facol.server;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@AllArgsConstructor
public class Messenger {

    private JSONServer server;

    public Confirmation sendRequest(UUID sessionIndex, Request request){

        final Confirmation[] reConfirmation = {null};

        try {
            server.send(sessionIndex, request).whenComplete((confirmation, throwable) ->
            {
                System.out.println(confirmation);
                reConfirmation[0] = confirmation;
            });
        } catch (OccurenceConstraintException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedFeatureException e) {
            throw new RuntimeException(e);
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
        return reConfirmation[0];
    }
}
