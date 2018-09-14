package za.ac.sun.cs.adversarial.domain;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 * This class represents the game state.
 */
public class Board {
    private final int m;
    private final int n;
    private final int k;
    private final int[][] board;

    public Board(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.board = new int[m][n];
    }

    /**
     * @return A list of all legal moves that can be made.
     */
    public ArrayList<Move> getLegalMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.board[i][j] == 0) {
                    moves.add(new Move(i, j));
                }
            }
        }

        return moves;
    }

    /**
     * Apply a move from the perspective of a given player onto the board.
     */
    public void makeMove(int player, Move move) {
        this.board[move.getRow()][move.getColumn()] = player;
    }

    /**
     * @param move The move to undo
     */
    public void undoMove(Move move) {
        this.board[move.getRow()][move.getColumn()] = 0;
    }

    /**
     * Check if the current state is terminal. If not, return the winning player.
     *
     * @return -1 if non-terminal, 0 if draw, 1 for player 1 win, 2 for player 2 win
     */
    public int isTerminal() {

        // Check Row wins
        for (int i = 0; i <= this.n - this.k; i++) {
            for (int j = 0; j < this.m; j++) {
                if (checkRowWin(1, j, i)) {
                    return 1;
                } else if (checkRowWin(2, j, i)) {
                    return 2;
                }
            }
        }

        // Check Column wins
        for (int i = 0; i <= this.n - this.k; i++) {
            for (int j = 0; j < this.n; j++) {
                if (checkColumnWin(1, j, i)) {
                    return 1;
                } else if (checkColumnWin(2, j, i)) {
                    return 2;
                }
            }
        }
        // Check Diagonal wins
        for (int i = 0; i <= this.n - this.k; i++) {
            for (int j = 0; j <= this.m - this.k; j++) {
                if (checkDiagonalWin(1, j, i)) {
                    return 1;
                } else if (checkDiagonalWin(2, j, i)) {
                    return 2;
                }
            }
        }

        // Check for a draw.
        if (getLegalMoves().size() == 0) {
            return 0;
        }


        // Nobody has won.
        return -1;
    }

    /**
     * @return True, if a horizontal win condition has been reached.
     */
    private boolean checkRowWin(int player, int row, int col) {
        for (int j = col; j < col + this.k; j++) {
            if (this.board[row][j] != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return True, if a vertical win condition has been reached.
     */
    private boolean checkColumnWin(int player, int col, int row) {
        for (int j = row; j < row + this.k; j++) {
            if (this.board[j][col] != player) {
                return false;
            }
        }

        return true;
    }


    /**
     * @return True, if a diagonal win condition has been reached.
     */
    private boolean checkDiagonalWin(int player, int row, int col) {
        boolean forward = true, reverse = true;
        // Check forward diagonal
        for (int j = 0; j < this.k; j++) {
            if (this.board[row + j][col + j] != player) {
                forward = false;
                break;
            }

        }

        // Check backward diagonal
        if ((this.n - col - this.k) >= 0) {
            for (int j = 0; j < this.k; j++) {
                if (this.board[row + j][this.n - col - j - 1] != player) {
                    reverse = false;
                    break;
                }
            }
        } else {
            reverse = false;
        }

        return (forward || reverse);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                String piece;
                if (board[i][j] == 0) {
                    piece = "-";
                } else {
                    piece = (board[i][j] == 1) ? "x" : "o";
                }
                sb.append(padLeft(piece, 2));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}