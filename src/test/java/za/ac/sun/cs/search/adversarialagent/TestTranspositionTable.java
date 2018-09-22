package za.ac.sun.cs.search.adversarialagent;

import java.util.HashSet;

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
        Board board = new Board(3, 3, 3);
        Zobrist hasher = new Zobrist(3, 3, 2);
        TranspositionTable table = new TranspositionTable(9);

        hasher.initialHash(board);

        board.makeMove(1, new Move(0, 0));

        table.put(hasher.getHash(), new TranspositionEntry(hasher.getHash(), new Move(0, 0), 1, Flag.UPPERBOUND, 3));

        TranspositionEntry entry = table.get(hasher.getHash());

        Assert.assertEquals(1, entry.getScore());
        Assert.assertEquals(Flag.UPPERBOUND, entry.getFlag());
        Assert.assertEquals(3, entry.getDepth());

    }

    @Test
    public void nullTest() {
        Board board = new Board(3, 3, 3);
        Zobrist hasher = new Zobrist(3, 3, 2);
        TranspositionTable table = new TranspositionTable(9);

        hasher.initialHash(board);

        board.makeMove(1, new Move(0, 0));

        TranspositionEntry entry = table.get(hasher.getHash());

        Assert.assertEquals(null, entry);


    }
  
}
