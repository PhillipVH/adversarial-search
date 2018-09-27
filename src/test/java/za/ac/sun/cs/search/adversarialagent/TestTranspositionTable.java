package za.ac.sun.cs.search.adversarialagent;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.hash.Zobrist;
import za.ac.sun.cs.adversarial.transposition.Flag;
import za.ac.sun.cs.adversarial.transposition.TranspositionTable;
import za.ac.sun.cs.adversarial.transposition.TranspositionEntry;

public class TestTranspositionTable {

    @Test
    public void smokeTest() {
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);
        TranspositionTable table = new TranspositionTable(9);

        hasher.initialHash(board);

        board.makeMove(1, new Move(0, 0));

        table.put(hasher.getHash(), new TranspositionEntry(hasher.getHash(), new Move(0, 0), 1, Flag.UPPERBOUND, 3));

        TranspositionEntry entry = table.get(hasher.getHash()).get();

        Assert.assertEquals(1, entry.getScore());
        Assert.assertEquals(Flag.UPPERBOUND, entry.getFlag());
        Assert.assertEquals(3, entry.getDepth());

    }

    @Test
    public void nullTest() {
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);
        TranspositionTable table = new TranspositionTable(9);

        hasher.initialHash(board);

        board.makeMove(1, new Move(0, 0));

        Optional<TranspositionEntry> entry = table.get(hasher.getHash());

        Assert.assertFalse(entry.isPresent());         

    }

    @Test
    public void replacementSchemeTest() {
        Board board = new Board(3, 3, 3, 1);
        Zobrist hasher = new Zobrist(3, 3);
        TranspositionTable table = new TranspositionTable(2);
        Optional<TranspositionEntry> entry;

        table.put(1, new TranspositionEntry(1, new Move(0, 1), 1, Flag.UPPERBOUND, 2));
        entry = table.get(1);
        Assert.assertTrue(entry.isPresent());

        table.put(5, new TranspositionEntry(5, new Move(0, 2), 1, Flag.UPPERBOUND,  4));
        entry = table.get(5);
        Assert.assertTrue(entry.isPresent());

        table.put(9, new TranspositionEntry(9, new Move(0, 3), 1, Flag.UPPERBOUND, 6));
        entry = table.get(9);
        Assert.assertTrue(entry.isPresent());

        entry = table.get(1);
        Assert.assertFalse(entry.isPresent());

        entry = table.get(5);
        Assert.assertTrue(entry.isPresent());
     
    }
  
}
