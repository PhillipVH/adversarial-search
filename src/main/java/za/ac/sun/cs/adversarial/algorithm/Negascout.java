package za.ac.sun.cs.adversarial.algorithm;

import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

/**
 * This class represents the NegaScout algorithm.
 */
public class Negascout {

    /**
     * The Negascout algorithm. (A Comparative Study of Game Tree Searching Methods, Fig 5)
     *
     * @return The value of the given node.
     */

    public static int NegaScout(Board board, int depth, int alpha, int beta, int player) {

        if (depth == 0 || (board.isTerminal()) != -1) {
            return player;
        }

        int score = Integer.MIN_VALUE;
        int n = beta;

        // Generate successors
        for (Move move : board.getLegalMoves()) {
            // Execute current move.
            board.makeMove(player, move);

            // Call other player.
            int current = -NegaScout(board, depth - 1, -n, -alpha, -player);

            if (current > score) {
                if (n == beta || depth <= 2) {
                    score = current;
                } else {
                    score = -NegaScout(board, depth - 1, -beta, -current, -player);
                }
            }

            // Adjust search window.
            if (score > alpha) {
                alpha = score;
            }

            // Undo move
            board.undoMove(move);

            // Cut offs.
            if (alpha >= beta) {
                return alpha;
            }
            n = alpha + 1;
        }

        return score;
    }

}
