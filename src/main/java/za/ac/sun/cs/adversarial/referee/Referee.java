package za.ac.sun.cs.adversarial.referee;

import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class representing a referee. Referees are responsible
 * from prompting moves from the two players, and also determining
 * who eventually wins.
 */
public class Referee {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        Board playerOneBoard = playerOne.getBoard();
        Board playerTwoBoard = playerTwo.getBoard();

        int roundCount = 1;

        while (true) {

            logger.info("Move " + roundCount);
            logger.info("\n" + playerOneBoard.toString());

            /* Check if we are in a terminal state. */
            if (playerOneBoard.isTerminal() > 0 || playerTwoBoard.isTerminal() > 0) {
                break;
            }

            /* Play a move from player one to player two. */
            playerOneMove = playerOne.requestMove();
            playerTwo.applyMove(playerOneMove);

            /* Check if we are in a terminal state. */
            if (playerOneBoard.isTerminal() > 0 || playerTwoBoard.isTerminal() > 0) {
                break;
            }


            logger.info("Move " + roundCount++);
            logger.info("\n" + playerOneBoard.toString());


            /* Play a move from player two to player one. */
            playerTwoMove = playerTwo.requestMove();
            playerOne.applyMove(playerTwoMove);
        }

        /* Report the results. */
        logger.info("Game ended. Final board state: ");
        logger.info("\n" + playerOneBoard.toString());

        switch (playerOne.getBoard().isTerminal()) {
            case -1:
                logger.info("Game is still in play.");
                break;
            case 0:
                logger.info("Draw.");
                break;
            case 1:
                logger.info("Player 1 wins.");
                break;
            case 2:
                logger.info("Player 2 wins.");
                break;
            default:
                logger.error("Something went horribly wrong!");
                break;
        }


    }
}
