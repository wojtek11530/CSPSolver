package sudoku;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;

import java.util.*;

public class SudokuConstraint implements CSPConstraint<Integer> {

    List<SudokuVariable> sudokuVariables;

    public SudokuConstraint(List<SudokuVariable> sudokuVariables) {
        this.sudokuVariables = sudokuVariables;
    }

    public boolean isSatisfied() {
        List<Integer> digits = new ArrayList<>();
        for (SudokuVariable variable: sudokuVariables) {
            Integer digit = variable.getVariableValue();
            if (digit != null) {
                digits.add(variable.getVariableValue());
            }
        }
        Map<Integer, Integer> result = new HashMap<>();
        for(Integer unique : new HashSet<>(digits)) {
            result.put(unique, Collections.frequency(digits, unique));
        }
        for (int count : result.values()) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean involvesVariable(CSPVariable<Integer> variable) {
        return sudokuVariables.contains(variable);
    }

    @Override
    public Set<Integer> getValuesToFilter(CSPVariable<Integer> emptyVariable, CSPVariable<Integer> variableWithValue) {
        Set<Integer> valuesToFilter = new HashSet<>();
        Integer value = variableWithValue.getVariableValue();

        if (emptyVariable.getFilteredDomain().contains(value)) {
            valuesToFilter.add(value);
        }
        return valuesToFilter;
    }

}
