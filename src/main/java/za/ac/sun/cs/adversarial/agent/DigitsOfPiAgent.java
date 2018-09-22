package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.LinkedList;
import java.util.List;

public class DigitsOfPiAgent extends Agent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DigitsOfPiBoard board;

    public DigitsOfPiAgent() {

        this.board = new DigitsOfPiBoard();
    }

    @Override
    public Move requestMove() {

        Negamax.F0(board, 4, 1);

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
}
