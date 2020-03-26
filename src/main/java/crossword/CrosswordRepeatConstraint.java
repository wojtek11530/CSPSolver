package crossword;

import abstraction.CSPConstraint;

public class CrosswordRepeatConstraint implements CSPConstraint {

    CrosswordVariable crosswordVariable1;
    CrosswordVariable crosswordVariable2;

    public CrosswordRepeatConstraint(CrosswordVariable crosswordVariable1, CrosswordVariable crosswordVariable2) {
        this.crosswordVariable1 = crosswordVariable1;
        this.crosswordVariable2 = crosswordVariable2;
    }

    public boolean isSatisfied() {
        if (crosswordVariable1.isEmpty() || crosswordVariable2.isEmpty()) {
            return true;
        } else {
            return !crosswordVariable1.getVariableValue().equals(crosswordVariable2.getVariableValue());
        }
    }
}
