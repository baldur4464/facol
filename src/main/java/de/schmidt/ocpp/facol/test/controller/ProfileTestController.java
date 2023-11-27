package de.schmidt.ocpp.facol.test.controller;


import de.schmidt.ocpp.facol.test.model.ProfileTest;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class ProfileTestController {

    private List<ProfileTest> profileTests;



    public ProfileTest getProfileTestBySessionUuid (UUID sessionUuid) {

        for(ProfileTest test: profileTests)
        {
            if(test.getSession().equals(sessionUuid)) {
                return test;
            }
        }
        return null;
    }

    public void addProfileTest (ProfileTest test) {
        profileTests.add(test);
    }

    public void updateProfileTest (int index, ProfileTest test){
        profileTests.get(index).setReporter(test.getReporter());
        profileTests.get(index).setSession(test.getSession());
    }
}
