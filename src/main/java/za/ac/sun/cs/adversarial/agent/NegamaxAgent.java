package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class NegamaxAgent extends Agent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Board board;
    private final int player;
    private final int depth;

    private String variant; // TODO Add variant system from DoP

    private int exploredNodes = 0;


    private final Negamax negamax;


    public NegamaxAgent(int m, int n, int k, int depth, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.depth = depth;

        /* Defaults to Alpha-Beta. */
        this.variant = "F2";
        this.negamax = new Negamax(m, n, "F2", false);


    }

    public NegamaxAgent(int m, int n, int k, int depth, int player, String variant) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.depth = depth;

        this.variant = variant;
        this.negamax = new Negamax(m, n, variant, false);

    }

    @Override
    public Move requestMove() {

        int bestValue = Integer.MIN_VALUE + 1;
        Move bestMove = null;

        List<Move> moves = board.getLegalMoves();

        /* Step through all the initial moves, selecting the best one. */
        for (Move move : moves) {
            board.makeMove(player, move);

            int value = Integer.MIN_VALUE + 1;
            switch (variant) {
                case "F0":
                    value = negamax.F0(board, depth, player);
                    break;
                case "F1":
                    value = negamax.F1(board, depth, Integer.MAX_VALUE, player);
                    break;
                case "F2":
                    value = negamax.F2(board, depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE, player);
                    break;
                case "F3":
                    break;

                default:
                    logger.error("Unsupported variant: " + variant);
            }

            exploredNodes += negamax.getExploredNodes();


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

    @Override
    public String reportStatistics() {
        StringBuilder statisticsBuilder = new StringBuilder();
        statisticsBuilder.append(String.format("exploredNodes = %d\n", exploredNodes));

        return statisticsBuilder.toString();
    }
}
