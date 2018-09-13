package za.ac.sun.cs.adversarial.domain;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Board {
    private final int m;
    private final int n;
    private final int k;
    private final int[][] board;
    private int player;

    public Board(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.board = new int[m][n];
        this.player = 1;
    }

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


    public void makeMove(int player, Move move) {
        this.board[move.getRow()][move.getColumn()] = player;
    }

    public void makeMove(int player, int i, int j) {
        this.board[i][j] = player;
    }

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

    public boolean checkRowWin(int player, int row, int col) {
        for (int j = col; j < col + this.k; j++) {
            if (this.board[row][j] != player) {
                return false;
            }
        }


        return true;
    }

    public boolean checkColumnWin(int player, int col, int row) {
        for (int j = row; j < row + this.k; j++) {
            if (this.board[j][col] != player) {
                return false;
            }
        }


        return true;
    }

    public boolean checkDiagonalWin(int player, int row, int col) {

        boolean forward = true, reverse = true;

        // Check forward diagonal
        for (int j = col; j < this.k; j++) {
            if (this.board[row + j][j] != player) {
                forward = false;
                break;
            }

        }

        // Check backward diagonal

        for (int j = col; j < this.k; j++) {
            if (this.board[row + j][this.n - j - 1] != player) {
                reverse = false;
                break;
            }
        }

        return (forward || reverse);
    }

    public int getPlayer() {
        return this.player;
    }

    public void nextPlayer() {
        switch (this.player) {
            case 0:
                this.player = 1;
                break;
            case 1:
                this.player = 0;
                break;
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}