package za.ac.sun.cs.adversarial.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class DigitsOfPiBoard implements Domain {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int depthTrigger = 4;
    private int currentDepth;
    private final List<Integer> ancestry;

    public DigitsOfPiBoard() {
        this.currentDepth = 1;
        this.ancestry = new LinkedList<>();
    }


    @Override
    public List<Move> getLegalMoves() {
        List<Move> moves = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            moves.add(new Move(i));
        }

        return moves;
    }

    @Override
    public int isTerminal() {
        return (currentDepth == depthTrigger) ? 1 : 0;
    }

    @Override
    public void makeMove(int player, Move move) {
        currentDepth++;
        ancestry.add(move.getId());
        logger.info("Depth after move: " + currentDepth);
        logger.info("Ancestry after move: " + ancestry.toString());
    }

    @Override
    public void undoMove(Move move) {
        currentDepth--;
        ancestry.remove(ancestry.size() - 1);
        logger.info("Depth after undo: " + currentDepth);
        logger.info("Ancestry after move: " + ancestry.toString());
    }

    public List<Integer> getAncestry() {
        return this.ancestry;
    }
}
