package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.Connector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChargepointRepositoryTest {

    @Autowired
    private ChargepointRepository repository;

    @Test
    public void addChargePoint() {

        Chargepoint chargepoint = Chargepoint.builder()
                .chargepointId("ChargePointTest")
                .build();


        repository.save(chargepoint);
    }
}