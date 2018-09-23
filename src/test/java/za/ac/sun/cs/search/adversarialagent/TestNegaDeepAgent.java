package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.agent.NegaDeepAgent;
import za.ac.sun.cs.adversarial.agent.RandomAgent;
import za.ac.sun.cs.adversarial.referee.Referee;

public class TestNegaDeepAgent {

    @Test
    public void smokeTest() {
        Agent playerOne = new NegaDeepAgent(3, 3, 3, 3,2);
        Agent playerTwo = new RandomAgent(3, 3, 3, 1);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }
}
