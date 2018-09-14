package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.RandomAgent;
import za.ac.sun.cs.adversarial.referee.Referee;
import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.agent.NegamaxAgent;

public class TestReferee {

    @Test
    public void smokeTest() {
        Agent playerOne = new NegamaxAgent(3, 3, 3, 10, 1);
        Agent playerTwo = new NegamaxAgent(3, 3, 3, 2, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }

    @Test
    public void randomAgents() {
        Agent playerOne = new RandomAgent(3, 3, 3, 1);
        Agent playerTwo = new RandomAgent(3, 3, 3, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }
}
