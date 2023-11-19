package de.schmidt.ocpp.facol.test.model;

import com.aventstack.extentreports.ExtentReports;
import de.schmidt.ocpp.facol.model.Session;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter @Builder
public class ProfileTest {

    private ExtentReports reporter;

    private UUID session;

}
