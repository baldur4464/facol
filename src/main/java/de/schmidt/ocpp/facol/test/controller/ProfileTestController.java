package de.schmidt.ocpp.facol.test.controller;


import de.schmidt.ocpp.facol.test.model.ProfileTest;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProfileTestController {

    private int count;

    private List<ProfileTest> profileTests;

    public ProfileTest getProfileTestBySessionUuid(UUID sessionUuid) {

        for (ProfileTest test : profileTests) {
            if (test.getSession().equals(sessionUuid)) {
                return test;
            }
        }
        return null;
    }

    public void addProfileTest(ProfileTest test) {
        test.setTestId(count);
        profileTests.add(test);
    }

    public void updateProfileTest(ProfileTest test) {
        profileTests.get(test.getTestId()).setReporter(test.getReporter());
        profileTests.get(test.getTestId()).setSession(test.getSession());
        profileTests.get(test.getTestId()).setConnectorId(test.getConnectorId());
        profileTests.get(test.getTestId()).setChargepointIdentifier(test.getChargepointIdentifier());
        profileTests.get(test.getTestId()).setTransactionId(test.getTransactionId());
        profileTests.get(test.getTestId()).setIdTag(test.getIdTag());
    }
}
