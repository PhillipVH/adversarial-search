package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.algorithm.Negascout;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class NegascoutAgent extends Agent {
    private final Board board;
    private final int player;
    private final int depth;

    public NegascoutAgent(int m, int n, int k, int depth, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.depth = depth;
    }

    @Override
    public Move requestMove() {

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        /* Step through all the initial moves, selecting the best one. */
        for (Move move : board.getLegalMoves()) {
            board.makeMove(player, move);

            int value = Negascout.NegaScout(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);

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
    public Board getBoard() {
        return this.board;
    }
}