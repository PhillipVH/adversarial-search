package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class NegamaxAgent extends Agent {
    private final Board board;
    private int player;

    public NegamaxAgent(int m, int n, int k, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
    }

    @Override
    public Move requestMove() {

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;

        List<Move> moves = board.getLegalMoves();

        /* Step through all the initial moves, selecting the best one. */
        for (Move move : moves) {
            board.makeMove(1, move);

            int value = Negamax.F2(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, player);

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
}
