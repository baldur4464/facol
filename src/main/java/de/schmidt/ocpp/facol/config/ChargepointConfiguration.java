package de.schmidt.ocpp.facol.config;


import de.schmidt.ocpp.facol.model.Chargepoint;
import de.schmidt.ocpp.facol.model.IdTag;
import de.schmidt.ocpp.facol.repository.ChargepointRepository;
import de.schmidt.ocpp.facol.repository.IdTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration ("chargepointConfig")
public class ChargepointConfiguration {

    @Autowired
    private ChargepointRepository chargepointRepository;

    @Autowired
    private IdTagRepository idTagRepository;

    public ChargepointConfiguration () {
        System.out.println("chargepointConfig geladen");
    }

    @Bean
    public CommandLineRunner run () throws Exception {
        return args -> {
            Chargepoint chargepoint = Chargepoint.builder()
                    .chargepointId("ChargePointTest")
                    .build();

            if(!chargepointRepository.existsById(chargepoint.getChargepointId())) {
                chargepointRepository.save(chargepoint);
                System.out.println("Chargepoint " + chargepoint.getChargepointId() + " wurde zur Datenbank hinzugeügt");
            } else {
                System.out.println("Chargepoint " + chargepoint.getChargepointId() + " existiert bereits");
            }

            IdTag idTag = IdTag.builder()
                    .idTagIdentifier("zero")
                    .build();

            if(idTagRepository.findByIdTagIdentifier(idTag.getIdTagIdentifier()) == null) {
                idTagRepository.save(idTag);
                System.out.println("IdTag " + idTag.getIdTagIdentifier() + " wurde zur Datenbank hinzugeügt");
            } else {
                System.out.println("IdTag " + idTag.getIdTagIdentifier() + " existiert bereits");
            }
        };
    }
}
