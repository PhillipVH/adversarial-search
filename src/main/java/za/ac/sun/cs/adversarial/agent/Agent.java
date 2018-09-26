package za.ac.sun.cs.adversarial.agent;

import za.ac.sun.cs.adversarial.domain.Domain;
import za.ac.sun.cs.adversarial.domain.Move;

public abstract class Agent {

    /**
     * @return The best move to be made according to the underlying
     * search algorithm, given the current board state.
     */
    public abstract Move requestMove();

    /**
     * Apply a move from an external source (i.e. another agent).
     */
    public abstract void applyMove(Move move);

    /**
     * @return The internal board state of the agent.
     */
    public abstract Domain getBoard();

    /**
     * @return Relevant statistics
     */
    public abstract String reportStatistics();
}
