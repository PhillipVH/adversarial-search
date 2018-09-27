package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.algorithm.Negascout;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

public class NegascoutAgent extends Agent {
    private final Board board;
    private final int player;
    private final int depth;

    /* Statistics */
        private int ttUpperboundCutoffs = 0;
        private int ttLowerboundCutoffs = 0;
        private int ttExactCutoffs = 0;
        private int ttAlphaBetaCutoffs = 0;
    
        private int exploredNodes = 0;
    
    
        private final Negascout negascout;

    public NegascoutAgent(int m, int n, int k, int depth, int player, boolean useTranspositionTable) {
        this.board = new Board(m, n, k, player);
        this.player = player;
        this.depth = depth;

        this.negascout = new Negascout(m, n, player, useTranspositionTable);

    }

    @Override
    public Move requestMove() {

        int bestValue = Integer.MIN_VALUE + 1;
        Move bestMove = null;
        /* Step through all the initial moves, selecting the best one. */
        for (Move move : board.getLegalMoves()) {
            board.makeMove(player, move);

            int value = negascout.NegaScout(board, depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE, player);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }

            exploredNodes += negascout.getExploredNodes();

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