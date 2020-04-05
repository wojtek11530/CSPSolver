
package solvers;

import abstraction.*;

import java.util.*;

public class MultiSolutionForwardCheckingEachStepSortingVar<T> implements CSPSolver<T> {

    private ConstraintSatisfactoryProblem<T> solvingProblem;
    private SolvingAlgorithmStatistics statistics;
    private List<CSPVariable<T>> variablesToIterate;
    private DomainOrderSetter<T> domainOrderSetter;
    private VariablesOrderSetter<T> variablesOrderSetter;


    @Override
    public boolean solve(ConstraintSatisfactoryProblem<T> problem,
                         VariablesOrderSetter<T> variablesOrderSetter,
                         DomainOrderSetter<T> domainOrderSetter) {
        this.solvingProblem = problem;
        this.statistics = new SolvingAlgorithmStatistics("Multi Solution Forward Algorithm Sorting Variables in each step");
        this.domainOrderSetter = domainOrderSetter;
        this.variablesOrderSetter = variablesOrderSetter;

        List<CSPVariable<T>> variables = problem.getVariables();

        statistics.start();
        variablesToIterate = new ArrayList<>(variables);
        tryAssignValueToVariable();
        statistics.setOverallTime();
        System.out.println(solvingProblem.getName());
        System.out.println(statistics);
        return statistics.isAnySolutionFound();
    }

    private void tryAssignValueToVariable() {

        variablesOrderSetter.setOrder(variablesToIterate);

        Iterator<CSPVariable<T>> variableIterator = variablesToIterate.iterator();
        CSPVariable<T> currentVariable = null;
        while (currentVariable == null && variableIterator.hasNext()) {
            CSPVariable<T> variable = variableIterator.next();
            if (variable.isEmpty()) {
                currentVariable = variable;
            }
        }

        if (currentVariable == null) {
            manageSolutionFinding();
        } else {

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

                        variablesToIterate.remove(currentVariable);
                        tryAssignValueToVariable();

                        variablesToIterate.add(currentVariable);
                        currentVariable.setVariableValue(null);

                    } else {
                        currentVariable.setVariableValue(null);
                        statistics.increaseOverallBacktrackNumber();
                    }
                    restoreFilteredValues(filteredValuesForVariables);
                } else {
                    currentVariable.setVariableValue(null);
                    statistics.increaseOverallBacktrackNumber();
                }
            }
            statistics.increaseOverallBacktrackNumber();
        }
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
