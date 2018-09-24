package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.agent.NegaDeepAgent;
import za.ac.sun.cs.adversarial.agent.RandomAgent;
import za.ac.sun.cs.adversarial.referee.Referee;

public class TestNegaDeepAgent {

    @Test
    public void smokeTest() {
        Agent playerOne = new NegaDeepAgent(3, 3, 3, 3,1);
        Agent playerTwo = new RandomAgent(3, 3, 3, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }


    @Test
    public void exposeBug1() {
        NegaDeepAgent playerOne = new NegaDeepAgent(5, 6, 4, 3,1);
        NegaDeepAgent playerTwo = new NegaDeepAgent(5, 6, 4, 5, 2, true);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();

        System.out.println(playerOne.reportStatistics());
    }

    @Test
    public void niceAndDeep() {
        NegaDeepAgent playerOne = new NegaDeepAgent(5, 5, 4, 5,1, false, true);
        NegaDeepAgent playerTwo = new NegaDeepAgent(5, 5, 4, 5, 2, true, true);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();

        System.out.println("Player 1");
        System.out.println(playerOne.reportStatistics());

        System.out.println("Player 2");
        System.out.println(playerTwo.reportStatistics());
    }

    @Test
    public void randomlyDeep() {
        RandomAgent playerOne = new RandomAgent(5, 5, 4, 1);
        NegaDeepAgent playerTwo = new NegaDeepAgent(5, 5, 4, 5, 2, true, true);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();

        System.out.println("Player 1");
//        System.out.println(playerOne.reportStatistics());

        System.out.println("Player 2");
        System.out.println(playerTwo.reportStatistics());
    }
}
