package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.MeterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterValueRepository extends JpaRepository<MeterValue, Long> {
}
