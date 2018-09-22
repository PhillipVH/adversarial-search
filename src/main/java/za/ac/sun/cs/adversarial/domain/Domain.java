package za.ac.sun.cs.adversarial.domain;

import java.util.List;

public interface Domain {
    List<Move> getLegalMoves();

    int isTerminal();

    void makeMove(int player, Move move);

    void undoMove(Move move);

    int getValue();
}
