package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

/**
 * The class representing the agent that interacts with
 * the transposition table enabled variant of Negamax.
 *
 * Unlink other variants, the transposition table and Zobrist
 * hashing facilities are all initialized when a new instance
 * of Negamax is instantiated.
 */
public class NegaDeepAgent extends Agent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Board board;
    private final int player;
    private final int depth;

    private final Negamax negamax;

    public NegaDeepAgent(int m, int n, int k, int depth, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.depth = depth;

        this.negamax = new Negamax(m, n, "F3");

    }

    @Override
    public Move requestMove() {

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;

        List<Move> moves = board.getLegalMoves();

        /* Step through all the initial moves, selecting the best one. */
        for (Move move : moves) {
            board.makeMove(player, move);

            int value = negamax.F3(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);


            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }

            board.undoMove(move);
        }

        /* Apply the move to our internal board and return to the callee. */
        board.makeMove(player, bestMove);
        return bestMove;
    }

    @Override
    public void applyMove(Move move) {
        board.makeMove(-player, move);
    }

    @Override
    public Domain getBoard() {
        return this.board;
    }
}
