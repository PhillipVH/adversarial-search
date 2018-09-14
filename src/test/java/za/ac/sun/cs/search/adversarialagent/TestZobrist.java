package za.ac.sun.cs.search.adversarialagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;

public class TestZobrist {

    @Test
    public void smokeTest() {
        Board board = new Board(3, 3, 3);
        Zobrist hasher = new Zobrist(3, 3, 2);

        hasher.initialHash(board);

        Assert.assertEquals(0, hasher.getHash());
    }

    @Test
    public void singleMove() {

        /* Initialize the board and the Zobrist hasher. */
        Board board = new Board(3, 3, 3);
        Zobrist hasher = new Zobrist(3, 3, 2);

        /* Hash in the empty board. */
        hasher.initialHash(board);

        Assert.assertEquals(0, hasher.getHash());

        /* Apply a move to the board. Update the hash. */
        Move move = new Move(0, 0);
        board.makeMove(1, move);

        hasher.hashIn(move, 1);

        long hash = hasher.getHash();

        Assert.assertNotEquals(0, hash);

        /* Undo the move. Ensure hash returns to previous state. */
        hasher.hashOut(move, 1);

        Assert.assertEquals(0, hasher.getHash());

    }
}
