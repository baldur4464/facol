package de.schmidt.ocpp.facol.config;

import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.Connector;
import de.schmidt.ocpp.facol.model.MeterValue;
import de.schmidt.ocpp.facol.model.Session;
import de.schmidt.ocpp.facol.model.Transaction;
import de.schmidt.ocpp.facol.repository.*;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.core.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration ("serverCoreProfileConfig")
@Getter
@Slf4j
public class ServerCoreProfileConfig {

    @Autowired
    private ChargepointRepository chargepointRepository;

    @Autowired
    private ConnectorRepository connectorRepository;

    @Autowired
    private IdTagRepository idTagRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MeterValueRepository meterValueRepository;

    @Bean
    public ServerCoreEventHandler getCoreEventHandler() {
        return new ServerCoreEventHandler() {
            @Override
            public AuthorizeConfirmation handleAuthorizeRequest(UUID sessionIndex, AuthorizeRequest request) {

                System.out.println(request);
                // ... handle event

                IdTagInfo idTagInfo = new IdTagInfo();
                idTagInfo.setExpiryDate(ZonedDateTime.now());
                idTagInfo.setParentIdTag("");
                idTagInfo.setStatus(AuthorizationStatus.Invalid);

                if(idTagRepository.findByIdTagIdentifier(request.getIdTag()) != null) {
                    idTagInfo.setExpiryDate(ZonedDateTime.now());
                    idTagInfo.setParentIdTag("");
                    idTagInfo.setStatus(AuthorizationStatus.Accepted);
                }
                return new AuthorizeConfirmation(idTagInfo);
            }

            @Override
            public BootNotificationConfirmation handleBootNotificationRequest(UUID sessionIndex, BootNotificationRequest request) {

                System.out.println(request);
                // ... handle event
                BootNotificationConfirmation notification = new BootNotificationConfirmation();
                notification.setCurrentTime(ZonedDateTime.now());
                notification.setInterval(0);
                notification.setStatus(RegistrationStatus.Rejected);

                Session session = sessionRepository.findSessionBySessionUuid(sessionIndex.toString());

                Chargepoint chargepoint = session.getChargepoint();
                if(chargepoint != null)
                {
                    chargepoint.setChargepointModel(request.getChargePointModel());
                    chargepoint.setChargepointVendor(request.getChargePointVendor());
                    chargepointRepository.save(chargepoint);

                    notification.setCurrentTime(ZonedDateTime.now());
                    notification.setInterval(120);
                    notification.setStatus(RegistrationStatus.Accepted);
                }
                return notification;
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {

                System.out.println(request);

                return new DataTransferConfirmation(); // returning null means unsupported feature
            }

            @Override
            public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {

                System.out.println(request);

                return new HeartbeatConfirmation(); // returning null means unsupported feature
            }

            @Override
            public MeterValuesConfirmation handleMeterValuesRequest(UUID sessionIndex, MeterValuesRequest request) {
                Transaction transaction;
                Optional<Transaction> transactionOpt = transactionRepository.findById(Long.valueOf(request.getTransactionId()));
                if(transactionOpt.isPresent())
                {
                    transaction = transactionOpt.get();
                    MeterValue meterValue = transaction.getMeterValue();
                }

                return new MeterValuesConfirmation(); // returning null means unsupported feature
            }

            @Override
            public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex, StartTransactionRequest request) {

                StartTransactionConfirmation transactionConfirmation = new StartTransactionConfirmation();

                System.out.println(request);
                // ... handle
                IdTagInfo idTagInfo = new IdTagInfo();
                idTagInfo.setExpiryDate(ZonedDateTime.now());
                idTagInfo.setParentIdTag("");
                idTagInfo.setStatus(AuthorizationStatus.Invalid);

                transactionConfirmation.setIdTagInfo(idTagInfo);

                if(idTagRepository.findByIdTagIdentifier(request.getIdTag()) != null) {
                    Session session = sessionRepository.findSessionBySessionUuid(sessionIndex.toString());

                    List<Connector> connectors = connectorRepository.findConnectorsByChargepointId(session.getChargepoint());

                    for(Connector connector: connectors) {
                        if(connector.getConnectorId().equals(Long.valueOf(request.getConnectorId())))
                        {
                            MeterValue meterValue = MeterValue.builder()
                                    .meterValue(0l)
                                    .meterValueTimestamp(ZonedDateTime.now())
                                    .build();
                            meterValueRepository.save(meterValue);

                            Transaction transaction = Transaction.builder()
                                    .startValue(Long.valueOf(request.getMeterStart()))
                                    .startTimestamp(request.getTimestamp())
                                    .connector(connector)
                                    .meterValue(meterValue)
                                    .chargepoint(connector.getChargepoint())
                                    .build();
                            Transaction savedTransaction = transactionRepository.save(transaction);

                            idTagInfo.setExpiryDate(ZonedDateTime.now());
                            idTagInfo.setParentIdTag("");
                            idTagInfo.setStatus(AuthorizationStatus.Accepted);

                            transactionConfirmation.setIdTagInfo(idTagInfo);
                            transactionConfirmation.setTransactionId(savedTransaction.getTransactionId().intValue());

                            return transactionConfirmation;
                        }
                    }
                }
                return transactionConfirmation; // returning null means unsupported feature
            }

            @Override
            public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex, StatusNotificationRequest request) {

                System.out.println(request);

                Session session = sessionRepository.findSessionBySessionUuid(sessionIndex.toString());
                List<Connector> connectors = connectorRepository.findConnectorsByChargepointId(session.getChargepoint());

                for (Connector connector: connectors) {
                    if(connector.getConnectorId() == Long.valueOf(request.getConnectorId())){
                        connector.setConnectorStatus(request.getStatus().toString());
                        connectorRepository.save(connector);
                        return new StatusNotificationConfirmation();
                    }
                }

                Connector connector = Connector.builder()
                        .connectorId(Long.valueOf(request.getConnectorId()))
                        .chargepoint(session.getChargepoint())
                        .connectorStatus(request.getStatus().toString())
                        .build();
                connectorRepository.save(connector);

                return new StatusNotificationConfirmation(); // returning null means unsupported feature
            }

            @Override
            public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex, StopTransactionRequest request) {

                System.out.println(request);

                StopTransactionConfirmation transactionConfirmation = new StopTransactionConfirmation();
                Optional<Transaction> transactionOpt = transactionRepository.findById(Long.valueOf(request.getTransactionId()));

                if(transactionOpt.isPresent())
                {

                    Transaction transaction = transactionOpt.get();

                    if(request.getTransactionId().equals(transaction.getTransactionId().intValue())) {
                        transaction.setStopValue(Long.valueOf(request.getMeterStop()));
                        transaction.setStopTimeStamp(request.getTimestamp());

                        MeterValue meterValue = transaction.getMeterValue();
                        meterValue.setMeterValue(transaction.getStopValue()-transaction.getStartValue());
                        meterValueRepository.save(meterValue);
                        transactionRepository.save(transaction);
                    }
                }

                IdTagInfo idTagInfo = new IdTagInfo();
                idTagInfo.setExpiryDate(ZonedDateTime.now());
                idTagInfo.setParentIdTag(null);
                idTagInfo.setStatus(AuthorizationStatus.Accepted);
                transactionConfirmation.setIdTagInfo(idTagInfo);

                return transactionConfirmation;
            }
        };
    }

    @Bean
    public ServerCoreProfile createCore(ServerCoreEventHandler serverCoreEventHandler) {
        return new ServerCoreProfile(serverCoreEventHandler);
    }
}
