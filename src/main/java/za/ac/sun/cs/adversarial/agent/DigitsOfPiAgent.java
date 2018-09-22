package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class DigitsOfPiAgent extends Agent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DigitsOfPiBoard board;
    private final String variant;
    private final int depth;

    public DigitsOfPiAgent(String variant) {
        this.board = new DigitsOfPiBoard();
        this.variant = variant;
        this.depth = 4;

    }

    @Override
    public Move requestMove() {

        int value;
        switch (variant) {
            case "F0":
                value = Negamax.F0(board, depth, 1);
                logger.info("Value at root: " + value);
                break;
            case "F1":
                value = Negamax.F1(board, depth, Integer.MAX_VALUE, 1);
                logger.info("Value at root: " + value);
                break;
            case "F2":
                value = Negamax.F2(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
                logger.info("Value at root: " + value);
                break;
            default:
                logger.error("Unknown variant of Negamax: " + variant);

        }

        return null;
    }

    @Override
    public void applyMove(Move move) {
        board.makeMove(-1, move);
    }

    @Override
    public Domain getBoard() {
        return this.board;
    }

    public List<Integer> getSampledDigits() {
        return this.board.getSampledDigits();
    }

    public List<Integer> getSampledIndices() {
        return this.board.getSampledIndices();
    }
}
