package de.schmidt.ocpp.facol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Connector {

    @Id
    @SequenceGenerator(
            name = "connector_sequence",
            sequenceName = "connector_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "connector_sequence"
    )
    private Long connectorPk;

    private Long connectorId;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "chargepoint_id",
            referencedColumnName = "chargepointId"
    )
    private Chargepoint chargepoint;

    private String connectorStatus;
}
