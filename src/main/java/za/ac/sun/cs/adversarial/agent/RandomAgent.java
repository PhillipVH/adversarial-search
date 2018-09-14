package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Move;

import java.util.List;
import java.util.Random;

/**
 * The class representing a random agent.
 */
public class RandomAgent extends Agent {

    private final Board board;
    private final int player;
    private final Random random;

    public RandomAgent(int m, int n, int k, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
        this.random = new Random();
    }

    @Override
    public Move requestMove() {

        List<Move> moves = board.getLegalMoves();

        Move randomMove = moves.get(random.nextInt(moves.size()));
        board.makeMove(player, randomMove);

        return randomMove;
    }

    @Override
    public void applyMove(Move move) {
        this.board.makeMove(-player, move);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
