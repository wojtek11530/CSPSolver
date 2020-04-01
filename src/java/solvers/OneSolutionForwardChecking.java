package solvers;

import abstraction.*;

import java.util.*;

public class OneSolutionForwardChecking<T> implements CSPSolver<T> {

    private ConstraintSatisfactoryProblem<T> solvingProblem;
    private SolvingAlgorithmStatistics statistics;
    private List<CSPVariable<T>> variablesToIterate;
    private DomainOrderSetter<T> domainOrderSetter;


    @Override
    public boolean solve(ConstraintSatisfactoryProblem<T> problem,
                         VariablesOrderSetter<T> variablesOrderSetter,
                         DomainOrderSetter<T> domainOrderSetter) {
        this.solvingProblem = problem;
        this.statistics = new SolvingAlgorithmStatistics("One Solution Forward Algorithm");
        this.domainOrderSetter = domainOrderSetter;

        List<CSPVariable<T>> variables = problem.getVariables();

        statistics.start();
        variablesToIterate = new ArrayList<>(variables);
        variablesOrderSetter.setOrder(variablesToIterate);
        int variableIndex = 0;

        tryAssignValueToVariable(variableIndex);
        statistics.setOverallTime();
        System.out.println(solvingProblem.getName());
        System.out.println(statistics);
        return statistics.isAnySolutionFound();
    }

    private boolean tryAssignValueToVariable(int variableIndex) {
        if (variableIndex == variablesToIterate.size()) {
            manageSolutionFinding();
            return true;
        }
        CSPVariable<T> currentVariable = variablesToIterate.get(variableIndex);
        Domain<T> variableDomain = currentVariable.getFilteredDomain();
        domainOrderSetter.setOrder(variableDomain);
        Iterator<T> domainIterator = variableDomain.iterator();

        while (domainIterator.hasNext()) {
            T value = domainIterator.next();
            currentVariable.setVariableValue(value);
            statistics.increaseOverallNodesNumber();

            boolean constraintsSatisfied = currentVariable.checkConstraints();
            if (constraintsSatisfied) {
                FilteringDomainsResult filteringDomainsResult = filterDomainsOfEmptyVariables(currentVariable);
                boolean noEmptyFilteredDomains = filteringDomainsResult.noEmptyFilteredDomains;
                Map<CSPVariable<T>, Set<T>> filteredValuesForVariables = filteringDomainsResult.filteredValuesForVariables;
                if (noEmptyFilteredDomains) {
                    variableIndex++;
                    boolean isSuccess = tryAssignValueToVariable(variableIndex);
                    if (isSuccess) {
                        return true;
                    } else {
                        variableIndex--;
                        currentVariable.setVariableValue(null);
                        restoreFilteredValues(filteredValuesForVariables);
                    }
                } else {
                    currentVariable.setVariableValue(null);
                    statistics.increaseOverallBacktrackNumber();
                    restoreFilteredValues(filteredValuesForVariables);
                }
            } else {
                currentVariable.setVariableValue(null);
                statistics.increaseOverallBacktrackNumber();
            }
        }
        statistics.increaseOverallBacktrackNumber();
        return false;
    }

    private void restoreFilteredValues(Map<CSPVariable<T>, Set<T>> filteredValuesForVariables) {
        for (CSPVariable<T> variable : filteredValuesForVariables.keySet()) {
            Set<T> valuesToRestore = filteredValuesForVariables.get(variable);
            variable.getFilteredDomain().addToDomain(valuesToRestore);
        }
    }

    private void manageSolutionFinding() {
        statistics.increaseSolutionsNumber();
        if (statistics.getSolutionsNumber() == 1) {
            statistics.setTimeToFirstSolution();
            statistics.setNodesNumberToFirstSolution();
            statistics.setBacktracksNumberToFirstSolution();
        }
        solvingProblem.addCurrentVariablesValuesAsSolution();
    }

    private FilteringDomainsResult filterDomainsOfEmptyVariables(CSPVariable<T> currentVariable) {
        Map<CSPVariable<T>, Set<T>> filteredValuesForVariables = new HashMap<>();
        boolean noEmptyFilteredDomains = true;
        for (CSPVariable<T> variable : variablesToIterate) {
            if (noEmptyFilteredDomains && variable.isEmpty()) {
                Set<T> filteredValues = variable.filterDomain(currentVariable);
                filteredValuesForVariables.put(variable, filteredValues);
                if (variable.getFilteredDomain().isEmpty()) {
                    noEmptyFilteredDomains = false;
                }
            }
        }
        return new FilteringDomainsResult(noEmptyFilteredDomains, filteredValuesForVariables);
    }

    private class FilteringDomainsResult {
        boolean noEmptyFilteredDomains;
        Map<CSPVariable<T>, Set<T>> filteredValuesForVariables;

        public FilteringDomainsResult(boolean noEmptyFilteredDomains, Map<CSPVariable<T>, Set<T>> filteredValuesForVariables) {
            this.noEmptyFilteredDomains = noEmptyFilteredDomains;
            this.filteredValuesForVariables = filteredValuesForVariables;
        }
    }


}
