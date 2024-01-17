package de.schmidt.ocpp.facol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Reservation {

    @Id
    @SequenceGenerator(
            name = "reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    private Long reservationId;

    private ZonedDateTime expiryDate;

    private String idTag;

    private String parentIdTag;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "connector_pk",
            referencedColumnName = "connectorPK"
    )
    private Connector connector;

}
