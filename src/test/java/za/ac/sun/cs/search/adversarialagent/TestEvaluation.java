package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

public class TestEvaluation {

    @Test
    public void testBoardRowWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(0, 1));
        board.makeMove(1, new Move(0, 2));
        assertEquals(15, board.getValue(1));
    }

    @Test
    public void testBoardColumnWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(1, 0));
        board.makeMove(1, new Move(2, 0));
        assertEquals(15, board.getValue(1));
    }

    @Test
    public void testBoardDiagonalFowardWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(1, 1));
        board.makeMove(1, new Move(2, 2));
        assertEquals(15, board.getValue(1));
    }

    @Test
    public void testBoardDiagonalBackwardWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 2));
        board.makeMove(1, new Move(1, 1));
        board.makeMove(1, new Move(2, 0));
        assertEquals(15, board.getValue(1));
    }

    @Test
    public void testEval() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(-1, new Move(0, 1));
        board.makeMove(1, new Move(0, 2));
        assertEquals(4, board.getValue(1));
    }

    @Test
    public void testOpponentEval() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(-1, new Move(0, 0));
        board.makeMove(-1, new Move(0, 1));
        board.makeMove(-1, new Move(0, 2));
        assertEquals(-15, board.getValue(1));
    }

    @Test
    public void testOpponentEval2() {
        Board board = new Board(4, 4, 4, 1);
        board.makeMove(-1, new Move(0, 0));
        board.makeMove(-1, new Move(0, 1));
        board.makeMove(-1, new Move(0, 2));
        board.makeMove(-1, new Move(0, 3));
        assertEquals(-22, board.getValue(1));
    }

}
