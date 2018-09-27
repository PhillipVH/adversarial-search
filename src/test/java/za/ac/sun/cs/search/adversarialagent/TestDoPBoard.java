package za.ac.sun.cs.search.adversarialagent;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.domain.DigitsOfPiBoard;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class TestDoPBoard {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void smokeTest() {
        DigitsOfPiBoard board = new DigitsOfPiBoard();

        List<Move> moves = board.getLegalMoves();

        Assert.assertEquals(3, moves.size());

        logger.info("Initial ancestry: " + board.getAncestry());

        Assert.assertEquals(0, board.getAncestry().size());

        board.makeMove(0, moves.get(2));

    }

    @Test
    public void makemoveUndomove() {
        DigitsOfPiBoard board = new DigitsOfPiBoard();

        List<Move> moves = board.getLegalMoves();

        Assert.assertEquals(3, moves.size());

        logger.info("Initial ancestry: " + board.getAncestry());

        Assert.assertEquals(0, board.getAncestry().size());

        board.makeMove(0, moves.get(2));

        board.undoMove(moves.get(2));

        Assert.assertEquals(0, board.getAncestry().size());
    }

    @Test
    public void terminalAtDepth() {

        DigitsOfPiBoard board = new DigitsOfPiBoard();

        for (int i = 0; i < 4; i++) {
            List<Move> moves = board.getLegalMoves();

            board.makeMove(0, moves.get(0));
        }

        Assert.assertEquals(3, board.getValue(0));
        Assert.assertEquals(1, board.isTerminal());
    }

    @Test
    public void samplingPi() {

        DigitsOfPiBoard board = new DigitsOfPiBoard();

        for (int i = 0; i < 3; i++) {
            List<Move> moves = board.getLegalMoves();

            board.makeMove(0, moves.get(i));
        }

        List<Move> moves = board.getLegalMoves();

        board.makeMove(0, moves.get(1));

        Assert.assertEquals(2, board.getValue(0));

    }
}
