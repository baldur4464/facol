package de.schmidt.ocpp.facol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chargepoint {

    @Id
    @SequenceGenerator(
            name = "chargepoint_sequence",
            sequenceName = "chargepoint_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chargepoint_sequence"
    )
    private Long chargepointId;

    private String chargepointIdentifier;

    private String chargepointModel;

    private String chargepointVendor;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "chargepoint_id",
            referencedColumnName = "chargepointId"
    )
    private List<Connector> connectors;
}
