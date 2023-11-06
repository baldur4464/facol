package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {
}
