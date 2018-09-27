package za.ac.sun.cs.adversarial.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeSet;
import java.util.Optional;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;

import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;
import za.ac.sun.cs.adversarial.transposition.Flag;
import za.ac.sun.cs.adversarial.transposition.TranspositionEntry;
import za.ac.sun.cs.adversarial.transposition.TranspositionTable;

/**
 * This class represents the NegaScout algorithm.
 */
public class Negascout {

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

    /**
     * The Negascout algorithm. (A Comparative Study of Game Tree Searching Methods,
     * Fig 5)
     *
     * @return The value of the given node.
     */

    public Negascout(int m, int n, boolean useTranspositionTable) {
        this.transpositionTable = new TranspositionTable(9);
        this.hasher = new Zobrist(m, n);
        this.useTranspositionTable = useTranspositionTable;

        if (useTranspositionTable) {
            logger.info("Initializing transposition table");
        }
    }

    public int NegaScout(Board board, int depth, int alpha, int beta, int player) {

        int alphaOrig = alpha;

        /* Ensure the hash is only initialized once. */
        hasher.initialHash(board);

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
                    alpha = Math.max(alpha, ttValue);
                } else if (ttFlag == Flag.UPPERBOUND) {
                    ttUpperboundCutoffs++;
                    logger.trace("Upper bound match from TT");
                    beta = Math.min(beta, ttValue);
                }

                if (alpha >= beta) {
                    ttAlphaBetaCutoffs++;
                    logger.trace("Cut-off from TT");
                    return ttValue;
                }
            }
        }

        if ((depth == 0) || board.isTerminal() != -1) {
            if (board.isTerminal() != -1) {
                if (board.isTerminal() == 0) {
                    return 0;
                } else {
                    return player * 10000;
                }
            } else {
                return board.getValue(player);
            }
        }

        int score = Integer.MIN_VALUE + 1;
        int n = beta;
        List<Move> moves = board.getLegalMoves();

        if (this.useTranspositionTable) {
            moves = orderMoves(moves, board, player, depth);
        } else {
            Collections.shuffle(moves);
        }

        // Generate successors
        for (Move move : moves) {
            /* Update statistics. */
            exploredNodes += 1;

            // Execute current move.
            board.makeMove(player, move);
            hasher.hashIn(move, player);

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
            hasher.hashOut(move, player);

            // Cut offs.
            if (alpha >= beta) {
                return alpha;
            }
            n = alpha + 1;
        }

        /* Store the state in the Transposition Table. */
        if (useTranspositionTable) {
            TranspositionEntry ttEntryRef = ttEntry.orElse(new TranspositionEntry());

            if (score <= alphaOrig) {
                ttEntryRef.setFlag(Flag.UPPERBOUND);
            } else if (score >= beta) {
                ttEntryRef.setFlag(Flag.LOWERBOUND);
            } else {
                ttEntryRef.setFlag(Flag.EXACT);
            }
            ttEntryRef.setScore(score);
            ttEntryRef.setKey(hasher.getHash());
            ttEntryRef.setDepth(depth);
            this.transpositionTable.put(hasher.getHash(), ttEntryRef);
        }

        return score;
    }

    /**
     * Order moves according to their statistics in the transposition table.
     *
     * @param moves The moves to be ordered
     */
    private List<Move> orderMoves(List<Move> moves, Board node, int player, int depth) {

        List<Move> orderedMoves = new LinkedList<>();

        LinkedList<TranspositionEntry> ttCandidates = new LinkedList<>();
        Map<Integer, Move> depthToMoves = new HashMap<>();

        for (Move move : moves) {
            node.makeMove(player, move);
            hasher.hashIn(move, player);

            /* Lookup the state in the Transposition Table. */
            Optional<TranspositionEntry> ttEntry;
            hasher.initialHash((Board) node);
            ttEntry = this.transpositionTable.get(hasher.getHash());

            /* Add the candidate entries to a list. */
            if (ttEntry.isPresent()) {
                ttCandidates.add(ttEntry.get());
                depthToMoves.put(ttEntry.get().getDepth(), move);
            }

            node.undoMove(move);
            hasher.hashOut(move, player);
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
