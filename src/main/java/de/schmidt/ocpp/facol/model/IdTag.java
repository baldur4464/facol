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
public class IdTag {

    @Id
    @SequenceGenerator(
            name = "idtag_sequence",
            sequenceName = "idtag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "idtag_sequence"
    )
    private Long idTagId;

    @Column(
            unique = true
    )
    private String idTagIdentifier;
}
