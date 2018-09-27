package za.ac.sun.cs.search.adversarialagent;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;

public class TestZobrist {

    @Test
    public void smokeTest() {
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);

        hasher.initialHash(board);

        Assert.assertEquals(0, hasher.getHash());
    }

    @Test
    public void singleMove() {

        /* Initialize the board and the Zobrist hasher. */
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);

        /* Hash in the empty board. */
        hasher.initialHash(board);

        Assert.assertEquals(0, hasher.getHash());

        /* Apply a move to the board. Update the hash. */
        Move move = new Move(0, 0);
        board.makeMove(1, move);

        hasher.hashIn(move, 1);

        long hash = hasher.getHash();
        System.out.println("Hash: " + hash);

        Assert.assertNotEquals(0, hash);

        /* Undo the move. Ensure hash returns to previous state. */
        hasher.hashOut(move, 1);

        Assert.assertEquals(0, hasher.getHash());

    }

    @Test
    public void uniqueHash() {
        HashSet<Long> hashes = new HashSet<Long>();

        /* Initialize the board and the Zobrist hasher. */
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);

        hasher.initialHash(board);


        /* Make moves and add them to the HashSet while checking that they are unique */
        Move move1 = new Move(0, 0);
        Move move2 = new Move(0, 1);
        Move move3 = new Move(0, 2);
        Move move4 = new Move(1, 0);
        Move move5 = new Move(1, 1);
        Move move6 = new Move(1, 2);
        Move move7 = new Move(2, 0);
        Move move8 = new Move(2, 1);
        Move move9 = new Move(2, 2);

        board.makeMove(1, move1);
        board.makeMove(2, move2);
        board.makeMove(1, move3);
        board.makeMove(2, move4);
        board.makeMove(1, move5);
        board.makeMove(2, move6);
        board.makeMove(1, move7);
        board.makeMove(2, move9);
        board.makeMove(1, move8);

        hasher.hashIn(move1, 1);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move2, 2);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move3, 1);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move4, 2);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move5, 1);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move6, 2);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move7, 1);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move9, 2);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

        hasher.hashIn(move8, 1);
        Assert.assertFalse(hashes.contains(hasher.getHash()));
        hashes.add(hasher.getHash());

    }

    @Test
    public void multiMove() {
        /* Initialize the board and the Zobrist hasher. */
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);

        hasher.initialHash(board);


        /* Make moves and add them to the HashSet while checking that they are unique */
        Move move1 = new Move(0, 0);
        Move move2 = new Move(0, 1);
        Move move3 = new Move(0, 2);
        Move move4 = new Move(1, 0);
        Move move5 = new Move(1, 1);
        Move move6 = new Move(1, 2);
        Move move7 = new Move(2, 0);
        Move move8 = new Move(2, 1);
        Move move9 = new Move(2, 2);

        board.makeMove(1, move1);
        board.makeMove(2, move2);
        board.makeMove(1, move3);
        board.makeMove(2, move4);
        board.makeMove(1, move5);
        board.makeMove(2, move6);
        board.makeMove(1, move7);
        board.makeMove(2, move9);
        board.makeMove(1, move8);

        hasher.hashIn(move1, 1);
        long hash1 = hasher.getHash();

        hasher.hashIn(move2, 2);
        long hash2 = hasher.getHash();

        hasher.hashIn(move3, 1);
        long hash3 = hasher.getHash();

        hasher.hashIn(move4, 2);
        long hash4 = hasher.getHash();

        hasher.hashIn(move5, 1);
        long hash5 = hasher.getHash();

        hasher.hashIn(move6, 2);
        long hash6 = hasher.getHash();

        hasher.hashIn(move7, 1);
        long hash7 = hasher.getHash();

        hasher.hashIn(move9, 2);
        long hash8 = hasher.getHash();

        hasher.hashIn(move8, 1);
        long hash9 = hasher.getHash();

        hasher.hashOut(move8, 1);
        Assert.assertEquals(hash8, hasher.getHash());

        hasher.hashOut(move9, 2);
        Assert.assertEquals(hash7, hasher.getHash());

        hasher.hashOut(move7, 1);
        Assert.assertEquals(hash6, hasher.getHash());

        hasher.hashOut(move6, 2);
        Assert.assertEquals(hash5, hasher.getHash());

        hasher.hashOut(move5, 1);
        Assert.assertEquals(hash4, hasher.getHash());

        hasher.hashOut(move4, 2);
        Assert.assertEquals(hash3, hasher.getHash());

        hasher.hashOut(move3, 1);
        Assert.assertEquals(hash2, hasher.getHash());

        hasher.hashOut(move2, 2);
        Assert.assertEquals(hash1, hasher.getHash());

        hasher.hashOut(move1, 1);
        Assert.assertEquals(0, hasher.getHash());


    }
}
