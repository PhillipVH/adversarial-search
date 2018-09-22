package za.ac.sun.cs.adversarial.algorithm;

import za.ac.sun.cs.adversarial.agent.DigitsOfPiAgent;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

/**
 * This class represents the Negamax algorithm.
 */
public class Negamax {

    /**
     * The pure minimax variation of Negamax. (Knuth75, page 296)
     * @return The value of the given node.
     */
    public static int F0(Domain node, int depth, int color) {
        if ((depth == 0) || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(color, move);

            value = max(value, -F0(node, depth - 1, -color));

            node.undoMove(move);
        }

        return value;

    }

    /**
     * The branch-and-bound variation of Negamax. (Knuth75, page 297)
     * @return The value of the given node.
     */
    public static int F1(Domain node, int depth, int bound, int color) {
        if ((depth == 0) || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(color, move);

            value = max(value, -F1(node, depth - 1, -bound, -color));

            node.undoMove(move);

            if (value >= bound) {
                break;
            }
        }

        return value;

    }

    /**
     * The alpha-beta variation of Negamax. (Knuth75, page 298)
     * @return The value of the given node.
     */
    public static int F2(Domain node, int depth, int alpha, int beta, int color) {
        if (depth == 0 || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves= node.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(color, move);

            value = max(value, -F2(node, depth - 1, -beta, -alpha, -color));

            node.undoMove(move);

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
