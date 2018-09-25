package za.ac.sun.cs.adversarial.referee;

import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.properties.AgentProperties;

/**
 * The class representing a driver for the referee.
 * It reads in two configuration files and parsing them to the referee to play.
 */
public class RefereeDriver {

    private String configOne;
    private String configTwo;

    public RefereeDriver(String configOne, String configTwo) {
        this.configOne = configOne;
        this.configTwo = configTwo;
    }

    public void playOut() {
        Agent playerOne = new AgentProperties(this.configOne).parseProperties(1);
        Agent playerTwo = new AgentProperties(this.configTwo).parseProperties(2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }

    public static void main(String[] args) {

        try {
            new RefereeDriver(args[0], args[1]).playOut();
        } catch (Exception e) {
            System.out.println("Please specify the two configuration files as arguments.");

        }
    }
}
