package za.ac.sun.cs.search.adversarialagent;

import java.beans.Transient;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import za.ac.sun.cs.search.adversarialagent.Board;


public class TestBoard {
   
    @Test
    public void testBoardRowWin() {
        Board board = new Board(3, 3, 3);
        board.makeMove(1, 0, 0);
        board.makeMove(1, 0, 1);
        board.makeMove(1, 0, 2);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardRowOtherWin() {
        Board board = new Board(3, 3, 3);
        board.makeMove(2, 0, 0);
        board.makeMove(2, 0, 1);
        board.makeMove(2, 0, 2);
        assertEquals(board.isTerminal(), 2);
    }

    @Test
    public void testBoardColumnWin() {
        Board board = new Board(3, 3, 3);
        board.makeMove(1, 0, 0);
        board.makeMove(1, 1, 0);
        board.makeMove(1, 2, 0);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalFowardWin() {
        Board board = new Board(3, 3, 3);
        board.makeMove(1, 0, 0);
        board.makeMove(1,1,1);
        board.makeMove(1,2,2);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalBackwardWin() {
        Board board = new Board(3 ,3, 3);
        board.makeMove(1, 0, 2);
        board.makeMove(1, 1, 1);
        board.makeMove(1, 2, 0);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDraw() {
        Board board = new Board(3 ,3, 3);
        board.makeMove(2 ,0, 0);
        board.makeMove(1 ,1, 1);
        board.makeMove(2 ,0, 2);
        board.makeMove(1 ,0, 1);
        board.makeMove(2 ,2, 1);
        board.makeMove(1 ,1, 2);
        board.makeMove(2 ,2, 2);
        board.makeMove(1 ,2, 0);
        board.makeMove(2 ,1, 0);
        assertEquals(board.isTerminal(), 0);
    }

    @Test
    public void testBoardNoWin() {
        Board board = new Board(3 ,3, 3);
        board.makeMove(1, 0, 0);
        board.makeMove(2, 2 ,2);
        board.makeMove(1, 1, 1);
        assertEquals(board.isTerminal(), -1);
    }

    @Test
    public void testBoardNoWin2() {
        Board board = new Board(3 ,3, 3);
        board.makeMove(1, 0 ,0);
        board.makeMove(2, 0 ,1);
        board.makeMove(1, 2 ,2);
        assertEquals(board.isTerminal(), -1);
    }   

    @Test
    public void testBoardRowWin2() {
        Board board = new Board(4 ,4, 2);
        board.makeMove(1, 1, 2);
        board.makeMove(1, 1, 3);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardColumnWin2() {
        Board board = new Board(4 ,4, 2);
        board.makeMove(1, 2, 1);
        board.makeMove(1, 3, 1);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalForwardWin2() {
        Board board = new Board(4 ,4, 2);
        board.makeMove(1, 2, 2);
        board.makeMove(1, 1, 1);
        assertEquals(board.isTerminal(), 1);
    }

    @Test
    public void testBoardDiagonalBackwardWin2() {
        Board board = new Board(4 ,4, 2);
        board.makeMove(1, 2, 1);
        board.makeMove(1, 1 , 2);
        assertEquals(board.isTerminal(), 1);
    }
}
