package za.ac.sun.cs.adversarial.algorithm;

import za.ac.sun.cs.adversarial.domain.MNKGame;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

/**
 * This class represents the Negamax algorithm.
 */
public class Negamax {

    /**
     * Example call: Negamax.search(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, 1)
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public static int search(MNKGame node, int depth, int alpha, int beta, int color) {
        if (depth == 0 || node.isTerminal()) {
            return color * node.value();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(move);

            value = max(value, -search(node, depth - 1, -beta, -alpha, -color));

            node.undoMove();

            alpha = max(alpha, value);

            if (alpha >= beta) {
                break;
            }
        }

        return value;

    }

    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }
}
