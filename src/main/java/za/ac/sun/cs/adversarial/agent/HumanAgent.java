package za.ac.sun.cs.adversarial.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.sun.cs.adversarial.domain.Board;
import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;
import za.ac.sun.cs.adversarial.referee.Referee;

import java.util.List;
import java.util.Scanner;

public class HumanAgent extends Agent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Board board;
    private final int player;

    public HumanAgent(int m, int n, int k, int player) {
        this.board = new Board(m, n, k);
        this.player = player;
    }
    @Override
    public Move requestMove() {

        List<Move> moves = board.getLegalMoves();

        Scanner scanner = new Scanner(System.in);
        int x, y;

        while (true) {

            try {
                System.out.println("Provide row-position: ");
                x = scanner.nextInt() - 1;

                System.out.println("Provide column-position: ");
                y = scanner.nextInt() - 1;

                Move humanMove = new Move(x, y);

                if (!moves.contains(humanMove)) {
                    System.out.println("Invalid move provided.");
                } else {
                    board.makeMove(player, humanMove);
                    return humanMove;
                }


            } catch (Exception ignored) {
                System.out.println("Invalid input. Try again.");

            }
        }


    }

    @Override
    public void applyMove(Move move) {
        this.board.makeMove(-player, move);
    }

    @Override
    public Domain getBoard() {
        return this.board;
    }

    public static void main (String[] args) {

        Agent playerOne = new NegaDeepAgent(5, 5, 3, 8,1, true, true);
        Agent playerTwo = new HumanAgent(5, 5, 3, 2);

        Referee referee = new Referee(playerOne, playerTwo);

        referee.runGame();
    }
}
