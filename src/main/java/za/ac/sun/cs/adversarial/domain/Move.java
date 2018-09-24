package za.ac.sun.cs.adversarial.domain;

import java.util.Objects;
import java.util.Optional;

/**
 * The class representing moves made in the (m,n,k)-domain, including
 * information such as the row and column of the move, as well as the
 * identifier of the player who made the move.
 */
public class Move {
    private final int row;
    private final int column;
    private final Optional<Integer> player;
    private int id;

    public Move(int row, int column) {
        this.row = row;
        this.column = column;
        this.player = Optional.empty();
    }

    public Move(int row, int column, int player) {
        this.row = row;
        this.column = column;
        this.player = Optional.of(player);
    }

    public Move(int id) {
        this.row = 0;
        this.column = 0;
        this.player = Optional.empty();

        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }

    @Override
    public boolean equals(Object o) {
        Move other = (Move) o;

        return ((this.column == other.column) && (this.row == other.row));
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}