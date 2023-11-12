package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findSessionBySessionUuid(String sessionIndex);
}
