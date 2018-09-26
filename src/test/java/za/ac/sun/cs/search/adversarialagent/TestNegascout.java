package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.RandomAgent;
import za.ac.sun.cs.adversarial.referee.Referee;
import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.agent.NegamaxAgent;
import za.ac.sun.cs.adversarial.agent.NegascoutAgent;


public class TestNegascout {

    @Test
    public void smokeTest() {
        Agent playerOne = new NegascoutAgent(3, 3, 3, 10, 1, true);
        Agent playerTwo = new RandomAgent(3, 3, 3, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }

    @Test
    public void PruningPlayers() {
        Agent playerOne = new NegascoutAgent(3, 3, 3, 10, 1, false);
        Agent playerTwo = new NegamaxAgent(3, 3, 3, 10, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }

    @Test
    public void PruningPlayers2() {
        Agent playerOne = new NegamaxAgent(3, 3, 3, 10, 1);
        Agent playerTwo = new NegascoutAgent(3, 3, 3, 10, 2, false);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }


    @Test
    public void largerRandomVNegascout() {
        Agent playerOne = new RandomAgent(5, 5, 5, 1);
        Agent playerTwo = new NegascoutAgent(5, 5, 5, 5, 1, false);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }

}