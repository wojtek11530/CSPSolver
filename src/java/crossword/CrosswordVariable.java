package crossword;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;
import abstraction.Domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrosswordVariable implements CSPVariable<String> {

    private String word;
    private Direction direction;
    private int length;
    private int beginningRowNumber;
    private int beginningColumnNumber;

    private Domain<String> domainWords;
    private Domain<String> filteredDomainWords;
    private List<CSPConstraint<String>> constraints = new ArrayList<>();

    public CrosswordVariable(Direction direction, int length, int beginningRowNumber, int beginningColumnNumber) {
        this.direction = direction;
        this.length = length;
        this.beginningRowNumber = beginningRowNumber;
        this.beginningColumnNumber = beginningColumnNumber;
    }

    public boolean isEmpty() {
        return word == null;
    }

    @Override
    public void setEmpty() {
        word = null;
    }

    public String getWord() {
        return word;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public int getBeginningRowNumber() {
        return beginningRowNumber;
    }

    public int getBeginningColumnNumber() {
        return beginningColumnNumber;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean checkConstraints() {
        boolean lengthConstraint;
        if (isEmpty()) {
            lengthConstraint = true;
        } else {
            lengthConstraint = word.length() == length;
        }
        if (!lengthConstraint) {
            return false;
        }
        for (CSPConstraint<String> constraint : constraints) {
            if (!constraint.isSatisfied()) {
                return false;
            }
        }
        return true;
    }

    public String getVariableValue() {
        return word;
    }

    public void setVariableValue(String word) {
        this.word = word;
    }

    public void addConstraint(CSPConstraint<String> constraint) {
        constraints.add(constraint);
    }

    public int getConstraintsNumber() {
        return constraints.size();
    }

    public Domain<String> getDomain() {
        return domainWords;
    }

    public void setDomain(Domain<String> domain) {
        domainWords = domain;
    }

    @Override
    public Set<String> filterDomain(CSPVariable<String> otherVariable) {
        Set<String> removedDomainValues = new HashSet<>();
        for (CSPConstraint<String> constraint : constraints) {
            if (constraint.involvesVariable(otherVariable)) {
                removedDomainValues.addAll(constraint.getValuesToFilter(this, otherVariable));
            }
        }

        filteredDomainWords.removeFromDomain(removedDomainValues);
        return removedDomainValues;
    }

    @Override
    public Domain<String> getFilteredDomain() {
        return filteredDomainWords;
    }

    @Override
    public void setFilteredDomain(Domain<String> domain) {
        filteredDomainWords = domain;
    }

    @Override
    public String toString() {
        return "crossword.CrosswordVariable{" +
                "word='" + word + '\'' +
                ", direction=" + direction +
                ", length=" + length +
                ", beginningRowNumber=" + beginningRowNumber +
                ", beginningColumnNumber=" + beginningColumnNumber +
                '}';
    }
}


