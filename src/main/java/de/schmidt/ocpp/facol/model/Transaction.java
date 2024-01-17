package de.schmidt.ocpp.facol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long transactionId;

    private ZonedDateTime startTimestamp;

    private ZonedDateTime stopTimeStamp;

    private Long startValue;

    private Long stopValue;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "connector_pk",
            referencedColumnName = "connectorPk"
    )
    private Connector connector;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "chargepoint_id",
            referencedColumnName = "chargepointId"
    )
    private Chargepoint chargepoint;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<MeterValue> meterValues;
}
