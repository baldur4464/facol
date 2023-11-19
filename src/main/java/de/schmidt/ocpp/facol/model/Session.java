package de.schmidt.ocpp.facol.model;

import com.aventstack.extentreports.ExtentReports;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {

    @Id
    @SequenceGenerator(
            name = "session_sequence",
            sequenceName = "session_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "session_sequence"
    )
    private Long sessionId;

    private String sessionUuid;

    @OneToOne
    @JoinColumn(
            name = "chargepoint_id",
            referencedColumnName = "chargepointId"
    )
    private Chargepoint chargepoint;
}
