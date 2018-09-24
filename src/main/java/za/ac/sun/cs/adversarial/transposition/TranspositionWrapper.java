package za.ac.sun.cs.adversarial.transposition;

/**
 * The class representing a pair of {@link TranspositionEntry}s.
 */
class TranspositionWrapper {
    
    private TranspositionEntry candidate;
    private TranspositionEntry second;

    TranspositionWrapper(TranspositionEntry candidate, TranspositionEntry second) {
        this.candidate = candidate;
        this.second = second;
    }

    TranspositionEntry getCandidate() {
        return this.candidate;
    }

    TranspositionEntry getSecond() {
        return this.second;
    }

    void setCandidate(TranspositionEntry candidate) {
        this.candidate = candidate;
    }

    void setSecond(TranspositionEntry second) {
        this.second = second;
    }

    void shiftCandidate() {
        this.second = this.candidate;
    }

    void reorder() {
        TranspositionEntry temp = this.candidate;

        if (second != null && candidate != null&& second.getDepth() >= this.candidate.getDepth()) {
            this.candidate = this.second;
            this.second = temp;
        }
    }
}