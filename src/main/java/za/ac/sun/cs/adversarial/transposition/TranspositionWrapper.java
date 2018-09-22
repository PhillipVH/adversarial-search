package za.ac.sun.cs.adversarial.transposition;

public class TranspositionWrapper {
    
    private TranspositionEntry candidate;
    private TranspositionEntry second;

    public TranspositionWrapper(TranspositionEntry candidate, TranspositionEntry second) {
        this.candidate = candidate;
        this.second = second;
    }

    public TranspositionEntry getCandidate() {
        return this.candidate;
    }

    public TranspositionEntry getSecond() {
        return this.second;
    }

    public void setCandidate(TranspositionEntry candidate) {
        this.candidate = candidate;
    }

    public void setSecond(TranspositionEntry second) {
        this.second = second;
    }

    public void shiftCandidate() {
        this.second = this.candidate;
    }

    public void reorder() {
        TranspositionEntry temp = this.candidate;

        if (this.second.getDepth() >= this.candidate.getDepth()) {
            this.candidate = this.second;
            this.second = temp;
        }
    }
}