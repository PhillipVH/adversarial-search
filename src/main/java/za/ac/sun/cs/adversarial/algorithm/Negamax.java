package za.ac.sun.cs.adversarial.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.agent.DigitsOfPiAgent;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;
import za.ac.sun.cs.adversarial.transposition.Flag;
import za.ac.sun.cs.adversarial.transposition.TranspositionEntry;
import za.ac.sun.cs.adversarial.transposition.TranspositionTable;

import java.util.*;

/**
 * This class represents the Negamax algorithm, and it's many variations.
 */
public class Negamax {

    /* Logging */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /* Hashing and TT */
    private final TranspositionTable transpositionTable;
    private final Zobrist hasher;

    /* Configuration */
    private boolean useTranspositionTable;

    /* Statistics */
    private int ttUpperboundCutoffs = 0;
    private int ttLowerboundCutoffs = 0;
    private int ttExactCutoffs = 0;
    private int ttAlphaBetaCutoffs = 0;

    private int exploredNodes = 0;
    private Domain rootNode;

    public Negamax() {
        this.transpositionTable = new TranspositionTable(9);
        this.hasher = null;
        this.useTranspositionTable = false;
    }

    /**
     * Constructor required for use of the iterative deepening variant of Negamax
     * with alpha-beta pruning and move ordering from the transposition table.
     *
     * @param m
     * @param n
     * @param variant
     */
    public Negamax(int m, int n, String variant, boolean useTranspositionTable) {
        this.transpositionTable = new TranspositionTable(9);
        this.hasher = new Zobrist(m, n);
        this.useTranspositionTable = useTranspositionTable;

        if (variant.equals("F3") && useTranspositionTable) {
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
    public int F0(Domain node, int depth, int color) {
        if ((depth == 0) || node.isTerminal() != -1) {
            if (node.isTerminal() != -1) {
                if (node.isTerminal() == 0) {
                    return 0;
                } else {
                    return color * 10000;
                }
            } else {
                return node.getValue(color);
            }
        }

        List<Move> moves = node.getLegalMoves();

        int value = Integer.MIN_VALUE + 1;

        for (Move move : moves) {
            node.makeMove(color, move);

            exploredNodes++;

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
    public int F1(Domain node, int depth, int bound, int color) {
        if ((depth == 0) || node.isTerminal() != -1) {
            if (node.isTerminal() != -1) {
                if (node.isTerminal() == 0) {
                    return 0;
                } else {
                    return color * 10000;
                }
            } else {
                return node.getValue(color);
            }
        }

        List<Move> moves = node.getLegalMoves();

        // Collections.shuffle(moves);

        int value = Integer.MIN_VALUE + 1;

        for (Move move : moves) {
            node.makeMove(color, move);

            exploredNodes++;

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
    public int F2(Domain node, int depth, int alpha, int beta, int color) {
        if ((depth == 0) || node.isTerminal() != -1) {
            if (node.isTerminal() != -1) {
                if (node.isTerminal() == 0) {
                    return 0;
                } else {
                    return color * 10000;
                }
            } else {
                return node.getValue(color);
            }
        }

        List<Move> moves = node.getLegalMoves();

        // Collections.shuffle(moves);

        int value = Integer.MIN_VALUE + 1;

        for (Move move : moves) {
            node.makeMove(color, move);

            exploredNodes++;

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
     * The alpha-beta variation of Negamax, with move ordering from a transposition
     * table. And they said F2 was the optimum!
     * <p>
     * (https://en.wikipedia.org/wiki/Negamax#Negamax_with_alpha_beta_pruning_and_transposition_tables)
     *
     * @return The value of the given node.
     */
    public int F3(Domain node, int depth, int alpha, int beta, int color) {

        int alphaOrig = alpha;

        /* Ensure the hash is only initialized once. */
        if (this.rootNode == null) {
            this.rootNode = node;
            hasher.initialHash((Board) node);
        }

        /* Lookup the state in the Transposition Table. */
        Optional<TranspositionEntry> ttEntry = Optional.empty();
        if (useTranspositionTable) {
            ttEntry = transpositionTable.get(hasher.getHash());

            if (ttEntry.isPresent() && ttEntry.get().getDepth() >= depth) {
                Flag ttFlag = ttEntry.get().getFlag();
                int ttValue = ttEntry.get().getScore();

                if (ttFlag == Flag.EXACT) {
                    logger.trace("Exact match from TT");
                    ttExactCutoffs++;
                    return ttEntry.get().getScore();
                } else if (ttFlag == Flag.LOWERBOUND) {
                    logger.trace("Lower bound match from TT");
                    ttLowerboundCutoffs++;
                    alpha = max(alpha, ttValue);
                } else if (ttFlag == Flag.UPPERBOUND) {
                    ttUpperboundCutoffs++;
                    logger.trace("Upper bound match from TT");
                    beta = min(beta, ttValue);
                }

                if (alpha >= beta) {
                    ttAlphaBetaCutoffs++;
                    logger.trace("Cut-off from TT");
                    return ttValue;
                }

            }
        }

        /* Core algorithm. */
        if ((depth == 0) || node.isTerminal() != -1) {
            if (node.isTerminal() != -1) {
                if (node.isTerminal() == 0) {
                    return 0;
                } else {
                    return color * 10000;
                }
            } else {
                return node.getValue(color);
            }
        }

        List<Move> moves = node.getLegalMoves();

        if (moves.size() == 0) {
            logger.debug("No legal moves");
            return color * node.getValue(color);
        }

        /*
         * Move ordering from TT if the flag is set, otherwise just shuffle the
         * collection.
         */
        if (useTranspositionTable) {
            moves = orderMoves(moves, node, color, depth);
        } else {
            // Collections.shuffle(moves);
        }

        int value = Integer.MIN_VALUE + 1;
        for (Move move : moves) {
            node.makeMove(color, move);
            hasher.hashIn(move, color);

            /* Update statistics. */
            exploredNodes += 1;

            value = max(value, -F3(node, depth - 1, -beta, -alpha, -color));

            node.undoMove(move);
            hasher.hashOut(move, color);

            alpha = max(alpha, value);

            if (alpha >= beta) {
                break;
            }
        }

        /* Store the state in the Transposition Table. */
        if (useTranspositionTable) {
            TranspositionEntry ttEntryRef = ttEntry.orElse(new TranspositionEntry());

            if (value <= alphaOrig) {
                ttEntryRef.setFlag(Flag.UPPERBOUND);
            } else if (value >= beta) {
                ttEntryRef.setFlag(Flag.LOWERBOUND);
            } else {
                ttEntryRef.setFlag(Flag.EXACT);
            }

            ttEntryRef.setScore(value);
            ttEntryRef.setKey(hasher.getHash());
            ttEntryRef.setDepth(depth);

            transpositionTable.put(hasher.getHash(), ttEntryRef);

        }

        return value;
    }

    /**
     * Order moves according to their statistics in the transposition table.
     *
     * @param moves The moves to be ordered
     */
    private List<Move> orderMoves(List<Move> moves, Domain node, int color, int depth) {

        List<Move> orderedMoves = new LinkedList<>();

        LinkedList<TranspositionEntry> ttCandidates = new LinkedList<>();
        Map<Integer, Move> depthToMoves = new HashMap<>();

        for (Move move : moves) {
            node.makeMove(color, move);
            hasher.hashIn(move, color);

            /* Lookup the state in the Transposition Table. */
            Optional<TranspositionEntry> ttEntry;
            hasher.initialHash((Board) node);
            ttEntry = transpositionTable.get(hasher.getHash());

            /* Add the candidate entries to a list. */
            if (ttEntry.isPresent()) {
                ttCandidates.add(ttEntry.get());
                depthToMoves.put(ttEntry.get().getDepth(), move);
            }

            node.undoMove(move);
            hasher.hashOut(move, color);
        }

        /* If no candidates were found, return the original list. */
        if (ttCandidates.isEmpty()) {
            return moves;
        }

        /* Sort the available candidate. */
        TreeSet<TranspositionEntry> ttCandidatesSorted = new TreeSet<>(ttCandidates);

        while (!ttCandidatesSorted.isEmpty()) {
            TranspositionEntry ttLast = ttCandidatesSorted.last();
            orderedMoves.add(depthToMoves.get(ttLast.getDepth()));

            ttCandidatesSorted.remove(ttLast);
        }

        /* Add the remaining moves last. */
        for (Move move : moves) {
            if (!orderedMoves.contains(move)) {
                orderedMoves.add(move);
            }
        }

        return orderedMoves;
    }

    /**
     * A helper function that returns the largest of its two arguments.
     *
     * @return The value of the largest argument
     */
    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     * A helper function that returns the largest of its two arguments.
     *
     * @return The value of the largest argument
     */
    private static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    public int getTTLowerboundCutoffs() {
        return ttLowerboundCutoffs;
    }

    public int getTTUpperboundCutoffs() {
        return ttUpperboundCutoffs;
    }

    public int getTTAlphaBetaCutoffs() {
        return ttAlphaBetaCutoffs;
    }

    public int getTTExactCutoffs() {
        return ttExactCutoffs;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }
}
