package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
