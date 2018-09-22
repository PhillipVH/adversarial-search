package za.ac.sun.cs.adversarial.transposition;

import java.util.Optional;

public class TranspositionTable {

    private int bits; // Number of bits to store.
    private int size; // Size of transposition table.
    private TranspositionWrapper[] table;

    public TranspositionTable(int bits) {
        this.bits = bits;
        this.size = 2 ^ bits;
        this.table = new TranspositionWrapper[this.size];
    }

    /**
     * Add a new {@link TranspositionEntry} into the table.
     * @param hash The hash of the board state.
     * @param entry The {@link TranspositionEntry} to be added.
     */
    public void put(long hash, TranspositionEntry entry) {
        int index = (int) (hash % this.size);
        TranspositionWrapper tw = this.table[index];

        // If there no entry at the index. Insert current entry as candidate.
        if (tw == null) {
            this.table[index] = new TranspositionWrapper(entry, null);
        } else if (tw.getCandidate().getKey() == hash || tw.getSecond().getKey() == hash) {
            // We have found the same state. If it has been explored deeper update it.
            if (tw.getCandidate().getKey() == hash) {
                if (entry.getDepth() > tw.getCandidate().getDepth()) {
                    tw.setCandidate(entry);
                    tw.reorder();
                }
            } else if (tw.getSecond().getKey() == hash) {
                if (entry.getDepth() > tw.getSecond().getDepth()) {
                    tw.setSecond(entry);
                    tw.reorder();
                }
            }
        } else {
            // 2DEEP replacement scheme
            if (entry.getDepth() >= tw.getCandidate().getDepth()) {
                tw.shiftCandidate();
                tw.setCandidate(entry);
            } else {
                tw.setSecond(entry);
            }
        }
    }

    /**
     * Recall a {@link TranspositionEntry} from the the transposition table.
     * @param hash The hash of the board state
     * @return The associated {@link TranspositionEntry}, if it is present
     */
    public Optional<TranspositionEntry> get(long hash) {
        int index = (int) (hash % this.size);
        TranspositionWrapper tw = this.table[index];

        if (tw == null) {
            return Optional.empty();
        }

        if (tw.getCandidate().getKey() == hash) {
            // Entry is in the candidate position.
            return Optional.of(tw.getCandidate());
        } else if (tw.getSecond().getKey() == hash) {
            // Entry is in the secondary position.
            return Optional.of(tw.getSecond());
        } else {
            // Entry was not found.
            return Optional.empty();
        }
    }

    public int getSize() {
        return this.size;
    }
}
