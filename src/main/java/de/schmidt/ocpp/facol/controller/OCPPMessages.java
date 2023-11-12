package de.schmidt.ocpp.facol.controller;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ResetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("ocppMessageComponent")
@Getter @Setter @NoArgsConstructor
public class OCPPMessages {

    private OCPPMessages instance;
    private ServerCoreProfile profile;
    private JSONServer server;

    public void sendRemoteStartTransactionMessage (UUID sessionId, int transactionId)
    {

        Request request = profile.createRemoteStopTransactionRequest(transactionId);
        try{
            server.send(sessionId, request).whenComplete(((confirmation, throwable) -> System.out.println(confirmation)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendRemoteStopTransactionMessage (UUID sessionId, String idTag)
    {
        Request request = profile.createRemoteStartTransactionRequest(idTag);
        try{
            server.send(sessionId, request).whenComplete(((confirmation, throwable) -> System.out.println(confirmation)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendSoftResetMessage (UUID sessionId)
    {
        Request request = profile.createResetRequest(ResetType.Soft);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendHardResetMessage (UUID sessionId)
    {
        Request request = profile.createResetRequest(ResetType.Hard);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendDataTransferMessage (UUID sessionId, String vendorId)
    {
        Request request = profile.createDataTransferRequest(vendorId);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
