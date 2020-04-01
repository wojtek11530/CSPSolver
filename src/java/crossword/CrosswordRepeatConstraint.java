package crossword;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;

import java.util.HashSet;
import java.util.Set;

public class CrosswordRepeatConstraint implements CSPConstraint<String> {

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

    @Override
    public boolean involvesVariable(CSPVariable<String> variable) {
        return crosswordVariable1 == variable || crosswordVariable2 == variable;
    }

    @Override
    public Set<String> getValuesToFilter(CSPVariable<String> emptyVariable, CSPVariable<String> variableWithValue) {
        Set<String> valuesToFilter = new HashSet<>();
        String value = variableWithValue.getVariableValue();

        if (emptyVariable.getFilteredDomain().contains(value)) {
            valuesToFilter.add(value);
        }
        return valuesToFilter;
    }
}
