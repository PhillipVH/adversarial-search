package za.ac.sun.cs.adversarial.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The class representing a Zobrist hash.
 */
public class Zobrist {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final long[][][] table;
    private final int m;
    private final int n;
    private long hash;

    /**
     * Initialize a Zobrist hash table.
     *
     * @param m The number of rows in the (m,n,k)-game board
     * @param n The number of columns in the (m,n,k)-game board
     */
    public Zobrist(int m, int n, int bitstringLength) {

        this.m = m;
        this.n = n;
        this.hash = 0L;

        /* Initialize the bitstring table. */
        table = new long[m][n][bitstringLength];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < bitstringLength; k++) {
                    table[i][j][k] = ThreadLocalRandom.current().nextLong();
                }
            }
        }

    }

    /**
     * @return The hash of a given board state.
     */
    public void initialHash(Board board) {
        long hash = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int piece = board.at(i, j);
                if (piece != 0) {
                    hash ^= this.table[i][j][piece];
                }
            }
        }
        this.hash = hash;
    }

    /**
     * @return The hash as calculated by Zobrist
     */
    public long getHash() {
        return this.hash;
    }

    /**
     * Roll back the hash by a single move.
     *
     * @param move The move to undo in the hash.
     * @param player The player making the move. 1 for player one, 2 for player 2.
     */
    public void hashOut(Move move, int player) {
        this.hash ^= table[move.getRow()][move.getColumn()][player];
    }

    /**
     * A proxy method to ease readability of hashing in/out.
     */
    public void hashIn(Move move, int player) {
        this.hashOut(move, player);
    }
}
