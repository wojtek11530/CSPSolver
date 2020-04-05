package sudoku;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;
import abstraction.Domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuVariable implements CSPVariable<Integer> {

    private Integer digit = null;
    private int rowNumber;
    private int columnNumber;
    private Domain<Integer> domainDigits;
    private Domain<Integer> filteredDomainDigits;
    private List<CSPConstraint<Integer>> constraints = new ArrayList<>();

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

    @Override
    public void setEmpty() {
        digit = null;
    }

    public boolean checkConstraints() {
        for (CSPConstraint<Integer> constraint : constraints) {
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

    public void addConstraint(CSPConstraint<Integer> constraint) {
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
    public Set<Integer> filterDomain(CSPVariable<Integer> otherVariable) {
        Set<Integer> removedDomainValues = new HashSet<>();
        for (CSPConstraint<Integer> constraint : constraints) {
            if (constraint.involvesVariable(otherVariable)) {
                removedDomainValues.addAll(constraint.getValuesToFilter(this, otherVariable));
            }
        }
        filteredDomainDigits.removeFromDomain(removedDomainValues);
        return removedDomainValues;
    }

    @Override
    public Domain<Integer> getFilteredDomain() {
        return filteredDomainDigits;
    }

    @Override
    public void setFilteredDomain(Domain<Integer> domain) {
        filteredDomainDigits = domain;
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
