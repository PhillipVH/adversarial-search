package za.ac.sun.cs.adversarial.transposition;

import za.ac.sun.cs.adversarial.domain.Move;

public class TranspositionEntry {

    private long key;
    private Move move;
    private int score;
    private int flag;
    private int depth;

    public TranspositionEntry(long key, Move move, int score, int flag, int depth) {
        this.key = key;
        this.move = move;
        this.score = score;
        this.flag = flag;
        this.depth = depth;
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

    public int getDepth() {
        return this.depth;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}