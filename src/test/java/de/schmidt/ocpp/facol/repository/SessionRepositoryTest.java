package de.schmidt.ocpp.facol.repository;

import de.schmidt.ocpp.facol.model.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SessionRepositoryTest {

    @Autowired
    private SessionRepository repository;
    
    @Test
    public void findAllSessions() {
       List<Session> sessions = repository.findAll();

        System.out.println("sessions = " + sessions);
    }
    
}