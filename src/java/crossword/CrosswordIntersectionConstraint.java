package crossword;

import abstraction.CSPConstraint;

public class CrosswordIntersectionConstraint implements CSPConstraint {

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
}
