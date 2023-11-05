package de.schmidt.ocpp.facol.controller;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ResetType;

import java.util.UUID;

public class OCPPMessages {

    private static OCPPMessages instance;
    private static ServerCoreProfile profile;
    private static JSONServer server;


    public static void SendRemoteStartTransactionMessage (UUID sessionId, int transactionId)
    {
        Request request = profile.createRemoteStopTransactionRequest(transactionId);
        try{
            server.send(sessionId, request).whenComplete(((confirmation, throwable) -> System.out.println(confirmation)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SendRemoteStopTransactionMessage (UUID sessionId, String idTag)
    {
        Request request = profile.createRemoteStartTransactionRequest(idTag);
        try{
            server.send(sessionId, request).whenComplete(((confirmation, throwable) -> System.out.println(confirmation)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SendSoftResetMessage (UUID sessionId)
    {
        Request request = profile.createResetRequest(ResetType.Soft);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void SendHardResetMessage (UUID sessionId)
    {
        Request request = profile.createResetRequest(ResetType.Hard);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void SendDataTransferMessage (UUID sessionId, String vendorId)
    {
        Request request = profile.createDataTransferRequest(vendorId);
        try{
            server.send(sessionId, request).whenComplete((confirmation, throwable) -> System.out.println(confirmation));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static OCPPMessages getInstance () {
        if(instance == null)
            instance = new OCPPMessages();
        return instance;
    }

    public void setProfile(ServerCoreProfile profile) {
        instance.profile = profile;
    }

    public static void setServer(JSONServer server) {
        instance.server = server;
    }

    private OCPPMessages () {}
}
