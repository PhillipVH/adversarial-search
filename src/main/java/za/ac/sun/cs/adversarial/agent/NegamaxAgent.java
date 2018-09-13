package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.algorithm.Negamax;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;

public class NegamaxAgent {
    public static void main(String[] args) {
        Board board = new Board(3, 3, 3);


        List<Move> moves= board.getLegalMoves();

        int value = Integer.MIN_VALUE;

        for (Move move : moves) {
            board.makeMove(1, move);

            int result = Negamax.F2(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            System.out.println("Move : " + move);
            System.out.println("Value : " + result);
            board.undoMove(move);

        }




    }
}
