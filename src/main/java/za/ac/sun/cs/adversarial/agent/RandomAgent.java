package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;
import java.util.Random;

/**
 * The class representing a random agent.
 */
public class RandomAgent extends Agent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Board board;
    private final int player;
    private final Random random;

    public RandomAgent(int m, int n, int k, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.random = new Random();
    }

    @Override
    public Move requestMove() {

        List<Move> moves = board.getLegalMoves();

        if (moves.size() == 0) {
            logger.info("No legal moves to be made.");
            return null;
        }

        Move randomMove = moves.get(random.nextInt(moves.size()));
        board.makeMove(player, randomMove);

        return randomMove;
    }

    @Override
    public void applyMove(Move move) {
        this.board.makeMove(-player, move);
    }

    @Override
    public Domain getBoard() {
        return this.board;
    }
}
