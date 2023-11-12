package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {

    @Query("SELECT c FROM Connector c where c.chargepoint = ?1")
    List<Connector> findConnectorsByChargepointId(Chargepoint chargepoint);
}
