package za.ac.sun.cs.adversarial.referee;

import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.properties.AgentProperties;

public class RefereeDriver {

    private String configOne;
    private String configTwo;

    public RefereeDriver(String configOne, String configTwo) {
        this.configOne = configOne;
        this.configTwo = configTwo;
    }

    public void playOut() {
        Agent playerOne = AgentProperties.parseProperties(this.configOne, 1);
        Agent playerTwo = AgentProperties.parseProperties(this.configTwo, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }
}
