package za.ac.sun.cs.adversarial.domain;

public class Move {
    int row;
    int column;

    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}