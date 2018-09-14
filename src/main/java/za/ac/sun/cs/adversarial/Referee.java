package za.ac.sun.cs.adversarial;

import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.domain.Move;

/**
 * The class representing a referee. Referees are responsible
 * from prompting moves from the two players, and also determining
 * who eventually wins.
 */
public class Referee {
    private final Agent playerOne;
    private final Agent playerTwo;

    public Referee(Agent playerOne, Agent playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    /**
     * The main game loop. Will prompt and pass along moves until the
     * game ends and a winner is announced.
     */
    public void runGame() {
        Move playerOneMove, playerTwoMove;

        while (true) {

            /* Check if we are in a terminal state. */
            if (playerOne.getBoard().isTerminal() > 0 || playerTwo.getBoard().isTerminal() > 0) {
                System.out.println("Game ended. Final board state:");
                System.out.println(playerOne.getBoard());
                return;
            }

            /* Play a move from player one to player two. */
            playerOneMove = playerOne.requestMove();
            playerTwo.applyMove(playerOneMove);

            /* Check if we are in a terminal state. */
            if (playerOne.getBoard().isTerminal() > 0 || playerTwo.getBoard().isTerminal() > 0) {
                System.out.println("Game ended. Final board state:");
                System.out.println(playerOne.getBoard());
                return;
            }

            /* Play a move from player two to player one. */
            playerTwoMove = playerTwo.requestMove();
            playerOne.applyMove(playerTwoMove);
        }

    }
}
