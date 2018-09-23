package za.ac.sun.cs.adversarial.transposition;

import za.ac.sun.cs.adversarial.domain.Move;

/**
 * This class represents the details of an entry
 * in a transposition table. It serves as a DTO,
 * with getters and setters where appropriate.
 */
public class TranspositionEntry {

    private long key;
    private Move move;
    private int score;
    private Flag flag;
    private int depth;

    public TranspositionEntry(long key, Move move, int score, Flag flag, int depth) {
        this.key = key;
        this.move = move;
        this.score = score;
        this.flag = flag;
        this.depth = depth;
    }

    public TranspositionEntry() {

    }

    public long getKey() {
        return this.key;
    }

    public Move getMove() {
        return this.move;
    }

    public int getScore() {
        return this.score;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}

