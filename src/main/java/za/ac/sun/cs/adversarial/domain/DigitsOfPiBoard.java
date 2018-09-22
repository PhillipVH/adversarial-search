package za.ac.sun.cs.adversarial.domain;

import java.util.List;

public class DigitsOfPiBoard implements Domain {
    private int depthTrigger;
    private int currentDepth;
    private int id;

    DigitsOfPiBoard(int depthTrigger) {
        this.depthTrigger = depthTrigger;
    }


    @Override
    public List<Move> getLegalMoves() {
        return null;
    }

    @Override
    public int isTerminal() {
        return 0;
    }

    @Override
    public void makeMove(int player, Move move) {

    }

    @Override
    public void undoMove(Move move) {

    }
}
