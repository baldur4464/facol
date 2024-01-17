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
public class Chargepoint {

    @Id
    private String chargepointId;

    private String chargepointModel;

    private String chargepointVendor;
}
