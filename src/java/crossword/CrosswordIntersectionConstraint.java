package crossword;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;

import java.util.HashSet;
import java.util.Set;

public class CrosswordIntersectionConstraint implements CSPConstraint<String> {

    CrosswordVariable rowVariable;
    CrosswordVariable columnVariable;

    public CrosswordIntersectionConstraint(CrosswordVariable rowVariable, CrosswordVariable columnVariable) {
        this.rowVariable = rowVariable;
        this.columnVariable = columnVariable;
    }

    public boolean isSatisfied() {
        int beginning_row = columnVariable.getBeginningRowNumber();
        int column = columnVariable.getBeginningColumnNumber();
        int beginning_column = rowVariable.getBeginningColumnNumber();
        int row = rowVariable.getBeginningRowNumber();

        if (rowVariable.isEmpty() || columnVariable.isEmpty()) {
            return true;
        } else return rowVariable.getVariableValue().charAt(column - beginning_column) ==
                columnVariable.getVariableValue().charAt(row - beginning_row);
    }

    @Override
    public boolean involvesVariable(CSPVariable<String> variable) {
        return rowVariable == variable || columnVariable == variable;
    }

    @Override
    public Set<String> getValuesToFilter(CSPVariable<String> emptyVariable, CSPVariable<String> variableWithValue) {
        CrosswordVariable emptyCrosswordVariable = (CrosswordVariable) emptyVariable;
        CrosswordVariable crosswordVariableWithValue = (CrosswordVariable) variableWithValue;

        String value = crosswordVariableWithValue.getVariableValue();
        Set<String> valuesToFilter = new HashSet<>();


        if (emptyCrosswordVariable == rowVariable && crosswordVariableWithValue == columnVariable) {
            int beginning_row = crosswordVariableWithValue.getBeginningRowNumber();
            int column = crosswordVariableWithValue.getBeginningColumnNumber();
            int beginning_column = emptyCrosswordVariable.getBeginningColumnNumber();
            int row = emptyCrosswordVariable.getBeginningRowNumber();

            for (String domainValue : emptyCrosswordVariable.getFilteredDomain().getDomainValues()) {
                if (domainValue.charAt(column - beginning_column) !=
                        value.charAt(row - beginning_row)) {
                    valuesToFilter.add(domainValue);
                }
            }
        } else if (emptyVariable == columnVariable && variableWithValue == rowVariable) {
            int beginning_row = emptyCrosswordVariable.getBeginningRowNumber();
            int column = emptyCrosswordVariable.getBeginningColumnNumber();
            int beginning_column = crosswordVariableWithValue.getBeginningColumnNumber();
            int row = crosswordVariableWithValue.getBeginningRowNumber();

            for (String domainValue : emptyCrosswordVariable.getFilteredDomain().getDomainValues()) {
                if (value.charAt(column - beginning_column) !=
                        domainValue.charAt(row - beginning_row)) {
                    valuesToFilter.add(domainValue);
                }
            }
        }
        return valuesToFilter;

    }
}
