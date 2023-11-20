package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.SampledValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampledValueRepository extends JpaRepository<SampledValue, Long> {
}
