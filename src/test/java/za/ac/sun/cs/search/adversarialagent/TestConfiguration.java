package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import za.ac.sun.cs.adversarial.referee.RefereeDriver;

public class TestConfiguration {

    @Test
    public void smokeTest() {
        String configOne = "config1.properties";
        String configTwo = "config2.properties";

        RefereeDriver ref = new RefereeDriver(configOne, configTwo);
        ref.playOut();
    }
}