package sudoku;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;
import abstraction.Domain;

import java.util.ArrayList;
import java.util.List;

public class SudokuVariable implements CSPVariable<Integer> {

    private Integer digit = null;
    private int rowNumber;
    private int columnNumber;
    private Domain<Integer> domainDigits;
    private List<CSPConstraint> constraints = new ArrayList<CSPConstraint>();

    public SudokuVariable(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public boolean isEmpty() {
        return digit == null;
    }

    public boolean checkConstraints() {
        for (CSPConstraint constraint : constraints) {
            if (!constraint.isSatisfied()) {
                return false;
            }
        }
        return true;
    }

    public Integer getVariableValue() {
        return digit;
    }

    public void setVariableValue(Integer value) {
        digit = value;
    }

    public void addConstraint(CSPConstraint constraint) {
        constraints.add(constraint);
    }

    public int getConstraintsNumber() {
        return constraints.size();
    }

    public Domain<Integer> getDomain() {
        return domainDigits;
    }

    public void setDomain(Domain<Integer> domain) {
        domainDigits = domain;
    }

    @Override
    public String toString() {
        return "SudokuVariable{" +
                "digit=" + digit +
                ", rowNumber=" + rowNumber +
                ", columnNumber=" + columnNumber +
                ", domainDigits=" + domainDigits +
                '}';
    }
}
