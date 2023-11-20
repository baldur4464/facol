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
public class SampledValue {

    @Id
    @SequenceGenerator(
            name = "sampled_value_sequence",
            sequenceName = "sampled_value_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sampled_value_sequence"
    )
    private Long sampleValueId;

    private Long rawValue;

    private String context;

    private String valueFormat;

    private String measurand;

    private String phase;

    private String location;

    private String unitOfMeasure;
}
