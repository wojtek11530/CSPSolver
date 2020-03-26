package sudoku;

import abstraction.CSPConstraint;

import java.util.*;

public class SudokuConstraint implements CSPConstraint {

    List<SudokuVariable> sudokuVariables;

    public SudokuConstraint(List<SudokuVariable> sudokuVariables) {
        this.sudokuVariables = sudokuVariables;
    }

    public boolean isSatisfied() {
        List<Integer> digits = new ArrayList<Integer>();
        for (SudokuVariable variable: sudokuVariables) {
            Integer digit = variable.getVariableValue();
            if (digit != null) {
                digits.add(variable.getVariableValue());
            }
        }
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        for(Integer unique : new HashSet<Integer>(digits)) {
            result.put(unique, Collections.frequency(digits, unique));
        }
        for (int count : result.values()) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

}
