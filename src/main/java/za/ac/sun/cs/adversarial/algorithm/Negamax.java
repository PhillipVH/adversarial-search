package za.ac.sun.cs.adversarial.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.agent.DigitsOfPiAgent;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;
import za.ac.sun.cs.adversarial.transposition.TranspositionEntry;
import za.ac.sun.cs.adversarial.transposition.TranspositionTable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the Negamax algorithm.
 */
public class Negamax {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TranspositionTable transpositionTable;
    private final Zobrist hasher;

    public Negamax(String variant) {
        this.transpositionTable = new TranspositionTable(9);
        this.hasher = new Zobrist()

        if (variant.equals("F3")) {
            logger.info("Initializing transposition table");
        } else {
            logger.error("Variant does not support initialization: " + variant);
        }
    }

    /**
     * The pure minimax variation of Negamax. (Knuth75, page 296)
     *
     * @return The value of the given node.
     */
    public static int F0(Domain node, int depth, int color) {
        if ((depth == 0) || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves = node.getLegalMoves();

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
     *
     * @return The value of the given node.
     */
    public static int F1(Domain node, int depth, int bound, int color) {
        if ((depth == 0) || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves = node.getLegalMoves();

        Collections.shuffle(moves);

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            node.makeMove(color, move);

            value = max(value, -F1(node, depth - 1, -value, -color));

            node.undoMove(move);

            if (value >= bound) {
                break;
            }
        }

        return value;

    }

    /**
     * The alpha-beta variation of Negamax. (Knuth75, page 298)
     *
     * @return The value of the given node.
     */
    public static int F2(Domain node, int depth, int alpha, int beta, int color) {
        if (depth == 0 || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves = node.getLegalMoves();

        Collections.shuffle(moves);

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
     * The alpha-beta variation of Negamax, with move ordering from
     * a transposition table.
     *
     * (https://en.wikipedia.org/wiki/Negamax#Negamax_with_alpha_beta_pruning_and_transposition_tables)
     *
     * @return The value of the given node.
     */
    public int F3(Domain node, int depth, int alpha, int beta, int color) {

        int alphaOrig = Integer.MAX_VALUE;

        /* Lookup the state in the Transposition Table. */
        Optional<TranspositionEntry> ttEntry = transpositionTable.get(123L);


        if (depth == 0 || node.isTerminal() > 0) {
            return color * node.getValue();
        }

        List<Move> moves = node.getLegalMoves();

        Collections.shuffle(moves);

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
     *
     * @return The value of the largest argument
     */
    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }
}
