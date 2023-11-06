package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
