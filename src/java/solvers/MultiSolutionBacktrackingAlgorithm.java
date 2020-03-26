package solvers;


import abstraction.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiSolutionBacktrackingAlgorithm<T> implements CSPSolver<T> {

    ConstraintSatisfactoryProblem<T> solvingProblem;
    BacktrackingAlgorithmStatistics statistics;
    List<CSPVariable<T>> variablesToIterate;
    private DomainOrderSetter<T> domainOrderSetter;


    public boolean solve(ConstraintSatisfactoryProblem<T> problem,
                         VariablesOrderSetter<T> variablesOrderSetter,
                         DomainOrderSetter<T> domainOrderSetter) {

        this.solvingProblem = problem;
        this.statistics = new BacktrackingAlgorithmStatistics();
        this.domainOrderSetter = domainOrderSetter;

        List<CSPVariable<T>>  variables = problem.getVariables();

        statistics.start();
        variablesToIterate = new ArrayList<CSPVariable<T>>(variables);
        variablesOrderSetter.setOrder(variablesToIterate);
        int variableIndex = 0;

        tryAssignValueToVariable(variableIndex);

        statistics.setOverallTime();
        System.out.println(solvingProblem.getName());
        System.out.println(statistics);
        return statistics.isAnySolutionFound();
    }

    private void tryAssignValueToVariable(int variableIndex) {

        if (variableIndex  == variablesToIterate.size()) {
            manageSolutionFinding();
        } else {
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
                    tryAssignValueToVariable(variableIndex);
                    variableIndex--;
                    currentVariable.setVariableValue(null);
                } else {
                    currentVariable.setVariableValue(null);
                    statistics.increaseOverallBacktrackNumber();
                }
            }
            statistics.increaseOverallBacktrackNumber();
        }
    }

    private void manageSolutionFinding() {
        statistics.increaseSolutionsNumber();
        if (statistics.getSolutionsNumber() == 1) {
            statistics.setTimeToFirstSolution();
            statistics.setNodesNumberToFirstSolution();
            statistics.setBacktracksNumberToFirstSolution();
            statistics.increaseOverallBacktrackNumber();
        }
        solvingProblem.addCurrentVariablesValuesAsSolution();
    }

}

