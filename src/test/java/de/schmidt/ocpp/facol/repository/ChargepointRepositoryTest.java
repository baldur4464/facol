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

        Connector connector = Connector.builder().build();

        Chargepoint chargepoint = Chargepoint.builder()
                .chargepointIdentifier("test")
                .chargepointVendor("test1")
                .connectors(List.of(connector))
                .build();


        repository.save(chargepoint);
    }
}