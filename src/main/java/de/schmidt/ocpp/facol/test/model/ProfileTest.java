package de.schmidt.ocpp.facol.test.model;

import com.aventstack.extentreports.ExtentReports;
import de.schmidt.ocpp.facol.model.Session;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter @Builder
public class ProfileTest {

    private int testId;

    private ExtentReports reporter;

    private UUID session;

    private String idTag;

    private String chargepointIdentifier;

    private long transactionId;

    private long connectorId;



    public ProfileTest (ExtentReports reporter, UUID sessionIndex) {
        this.reporter = reporter;
        this.session = sessionIndex;
    }

}
