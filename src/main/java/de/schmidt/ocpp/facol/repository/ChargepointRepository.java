package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Chargepoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargepointRepository extends JpaRepository<Chargepoint, Long> {
}
