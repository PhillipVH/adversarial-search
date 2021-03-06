package za.ac.sun.cs.adversarial.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * The class representing the Digits of Pi domain from Knuth and Moore's "An
 * Analysis of Alpha-Beta Pruning", p299.
 */
public class DigitsOfPiBoard implements Domain {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int currentDepth;
    private final List<Integer> ancestry;
    private final List<Integer> sampledDigits;
    private final List<Integer> sampledIndices;

    public DigitsOfPiBoard() {
        this.currentDepth = 0;
        this.ancestry = new LinkedList<>();
        this.sampledDigits = new LinkedList<>();
        this.sampledIndices = new LinkedList<>();
    }

    @Override
    public List<Move> getLegalMoves() {
        List<Move> moves = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            moves.add(new Move(i));
        }

        return moves;
    }

    /**
     * @return 1 if terminal, -1 if non-terminal
     */
    @Override
    public int isTerminal() {
        int depthTrigger = 4;
        return (currentDepth == depthTrigger) ? 1 : -1;
    }

    /**
     * @return The digit of Pi that this state maps to
     */
    @Override
    public int getValue(int player) {
        int idx = DigitsOfPi.getIndex(ancestry);
        int digit = DigitsOfPi.samplePi(idx);

        sampledDigits.add(digit);
        sampledIndices.add(idx + 1);

        return digit;
    }

    public List<Integer> getSampledDigits() {
        return this.sampledDigits;
    }

    public List<Integer> getSampledIndices() {
        return this.sampledIndices;
    }

    @Override
    public void makeMove(int player, Move move) {
        currentDepth++;
        ancestry.add(move.getId());
        logger.trace("Depth after move: " + currentDepth);
        logger.trace("Ancestry after move: " + ancestry.toString());
    }

    @Override
    public void undoMove(Move move) {
        currentDepth--;
        ancestry.remove(ancestry.size() - 1);
        logger.trace("Depth after undo: " + currentDepth);
        logger.trace("Ancestry after move: " + ancestry.toString());
    }

    public List<Integer> getAncestry() {
        return this.ancestry;
    }
}

/**
 * The class used to sample digits of Pi.
 */
class DigitsOfPi {

    private static final Logger logger = LoggerFactory.getLogger("DigitsOfPi");

    private static final int[] digits = new int[] { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 6,
            4, 3, 3, 8, 3, 2, 7, 9, 5, 0, 2, 8, 8, 4, 1, 9, 7, 1, 6, 9, 3, 9, 9, 3, 7, 5, 1, 0, 5, 8, 2, 0, 9, 7, 4, 9,
            4, 4, 5, 9, 2, 3, 0, 7, 8, 1, 6, 4, 0, 6, 2, 8, 6, 2, 0, 8, 9, 9 };

    /**
     * @return The index into the digits of Pi to which the ancestry record maps, or
     *         -1 in case of an error.
     */
    static int getIndex(List<Integer> ancestry) {
        int[] candidate = new int[] { 1, 1, 1, 1 };

        for (int i = 0; i < 81; i++) {
            if (match(candidate, ancestry)) {
                return i;
            } else {
                /* Increment the candidate. */
                tick(candidate);
            }
        }

        return -1;
    }

    /**
     * @return The digit of Pi that the index corresponds to.
     */
    static int samplePi(int idx) {
        return digits[idx];
    }

    /**
     * Generates the next candidate along
     *
     * @param candidate A reference to the current candidate
     */
    private static void tick(int[] candidate) {

        /* Start by incrementing the last digit if possible. */
        if (candidate[3] < 3) {
            candidate[3]++;
            return;
        }

        /* Increment the last position and roll over. */
        candidate[3] = 1;

        if (candidate[2] < 3) {
            candidate[2]++;
            return;
        }

        /* Increment the second last position and roll over. */
        candidate[2] = 1;

        if (candidate[1] < 3) {
            candidate[1]++;
            return;
        }

        candidate[1] = 1;

        if (candidate[0] < 3) {
            candidate[0]++;
            return;
        }

        candidate[0] = 1;

        logger.error("Candidate has rolled over.");

    }

    /**
     * @return True if the candidate matches the history, false otherwise.
     */
    private static boolean match(int[] candidate, List<Integer> ancestry) {
        for (int i = 0; i < candidate.length; i++) {
            if (candidate[i] != ancestry.get(i)) {
                return false;
            }
        }

        return true;
    }
}
