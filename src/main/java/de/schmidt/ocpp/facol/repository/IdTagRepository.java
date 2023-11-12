package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.IdTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdTagRepository extends JpaRepository<IdTag,Long> {

    IdTag findByIdTagIdentifier (String idTagIdentifier);
}
