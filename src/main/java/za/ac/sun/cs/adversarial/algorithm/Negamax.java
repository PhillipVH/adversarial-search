package za.ac.sun.cs.adversarial.algorithm;

import za.ac.sun.cs.adversarial.domain.MNKGame;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

/**
 * This class represents the Negamax algorithm.
 */
public class Negamax {

    /**
     * The branch-and-bound variation of Negamax. (Knuth75, page 297)
     * @param node
     * @param depth
     * @param bound
     * @param color
     * @return
     */
    public static int F1(MNKGame node, int depth, int bound, int color) {
        if (depth == 0 || node.isTerminal()) {
            return color * node.value();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(move);

            value = max(value, -F1(node, depth - 1, -bound, -color));

            node.undoMove();

            if (value >= bound) {
                break;
            }
        }

        return value;

    }

    /**
     * The alpha-beta variation of Negamax. (Knuth75, page 298)
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public static int F2(MNKGame node, int depth, int alpha, int beta, int color) {
        if (depth == 0 || node.isTerminal()) {
            return color * node.value();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(move);

            value = max(value, -F2(node, depth - 1, -beta, -alpha, -color));

            node.undoMove();

            alpha = max(alpha, value);

            if (alpha >= beta) {
                break;
            }
        }

        return value;
    }

    /**
     * A helper function that returns the largest of its two arguments.
     * @return The value of the largest argument
     */
    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }
}
