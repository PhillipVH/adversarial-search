package za.ac.sun.cs.search.adversarialagent;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;


public class TestBoard {
   
    @Test
    public void testBoardRowWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(0, 1));
        board.makeMove(1, new Move(0, 2));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardRowOtherWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(-1, new Move(0, 0));
        board.makeMove(-1, new Move(0, 1));
        board.makeMove(-1, new Move(0, 2));
        assertEquals(board.isTerminal(), 2);
    }

    @Test
    public void testBoardColumnWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(1, 0));
        board.makeMove(1, new Move(2, 0));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalFowardWin() {
        Board board = new Board(3, 3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(1, new Move(1,1));
        board.makeMove(1, new Move(2,2));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalBackwardWin() {
        Board board = new Board(3 ,3, 3, 1);
        board.makeMove(1, new Move(0, 2));
        board.makeMove(1, new Move(1, 1));
        board.makeMove(1, new Move(2, 0));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDraw() {
        Board board = new Board(3 ,3, 3, 1);
        board.makeMove(-1 , new Move(0, 0));
        board.makeMove(1 , new Move(1, 1));
        board.makeMove(-1 , new Move(0, 2));
        board.makeMove(1 , new Move(0, 1));
        board.makeMove(-1 , new Move(2, 1));
        board.makeMove(1 , new Move(1, 2));
        board.makeMove(-1 , new Move(2, 2));
        board.makeMove(1 , new Move(2, 0));
        board.makeMove(-1 , new Move(1, 0));
        assertEquals(board.isTerminal(), 0);
    }

    @Test
    public void testBoardNoWin() {
        Board board = new Board(3 ,3, 3, 1);
        board.makeMove(1, new Move(0, 0));
        board.makeMove(-1, new Move(2 ,2));
        board.makeMove(1, new Move(1, 1));
        assertEquals(board.isTerminal(), -1);
    }

    @Test
    public void testBoardNoWin2() {
        Board board = new Board(3 ,3, 3, 1);
        board.makeMove(1, new Move(0 ,0));
        board.makeMove(-1, new Move(0 ,1));
        board.makeMove(1, new Move(2 ,2));
        assertEquals(board.isTerminal(), -1);
    }   

    @Test
    public void testBoardRowWin2() {
        Board board = new Board(4 ,4, 2, 1);
        board.makeMove(1, new Move(1, 2));
        board.makeMove(1, new Move(1, 3));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardColumnWin2() {
        Board board = new Board(4 ,4, 2, 1);
        board.makeMove(1, new Move(2, 1));
        board.makeMove(1, new Move(3, 1));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalForwardWin2() {
        Board board = new Board(4 ,4, 2, 1);
        board.makeMove(1, new Move(2, 2));
        board.makeMove(1, new Move(1, 1));
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalBackwardWin2() {
        Board board = new Board(4 ,4, 2, 1);
        board.makeMove(1, new Move(2, 1));
        board.makeMove(1, new Move(1 , 2));
        assertEquals(board.isTerminal(), 1);
    }
}
