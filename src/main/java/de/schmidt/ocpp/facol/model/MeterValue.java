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
public class MeterValue {

    @Id
    @SequenceGenerator(
            name = "meter_value_sequence",
            sequenceName = "meter_value_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meter_value_sequence"
    )
    private Long meterValueId;

    private ZonedDateTime meterValueTimestamp;

    private Long meterValue;
}
