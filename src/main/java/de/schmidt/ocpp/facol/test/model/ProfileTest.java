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

    private boolean canTest;

    private boolean isBootNotificationTested = false;

    private boolean isRemoteStartTransactionTested = false;

    private boolean isRemoteStopTransactionTested = false;

    private boolean isHeartbeatTested = false;

    private boolean isStatusNotificationTested = false;

    private boolean isTriggerMessageTested = false;

    private boolean isAuhorizeTested = false;

    public ProfileTest (ExtentReports reporter, UUID sessionIndex, boolean canTest) {
        this.reporter = reporter;
        this.session = sessionIndex;
        this.canTest = canTest;
    }

}
