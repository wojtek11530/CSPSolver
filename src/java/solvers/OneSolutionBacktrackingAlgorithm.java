package solvers;


import abstraction.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OneSolutionBacktrackingAlgorithm<T> implements CSPSolver<T> {

    private ConstraintSatisfactoryProblem<T> solvingProblem;
    private SolvingAlgorithmStatistics statistics;
    private List<CSPVariable<T>> variablesToIterate;
    private DomainOrderSetter<T> domainOrderSetter;

    public boolean solve(ConstraintSatisfactoryProblem<T> problem,
                         VariablesOrderSetter<T> variablesOrderSetter,
                         DomainOrderSetter<T> domainOrderSetter) {

        this.solvingProblem = problem;
        this.statistics = new SolvingAlgorithmStatistics("One Solution Backtracking Algorithm");
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
        Domain<T> variableDomain = currentVariable.getDomain();
        domainOrderSetter.setOrder(variableDomain);
        Iterator<T> domainIterator = variableDomain.iterator();

        while (domainIterator.hasNext()) {
            T value = domainIterator.next();
            currentVariable.setVariableValue(value);
            statistics.increaseOverallNodesNumber();

            boolean constraintsSatisfied = currentVariable.checkConstraints();
            if (constraintsSatisfied) {
                variableIndex++;
                boolean isSuccess = tryAssignValueToVariable(variableIndex);
                if (isSuccess) {
                    return true;
                } else {
                    variableIndex--;
                    currentVariable.setVariableValue(null);
                }
            } else {
                currentVariable.setVariableValue(null);
                statistics.increaseOverallBacktrackNumber();
            }
        }
        statistics.increaseOverallBacktrackNumber();
        return false;
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

}

