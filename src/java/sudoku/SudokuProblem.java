package sudoku;

import abstraction.CSPConstraint;
import abstraction.CSPVariable;
import abstraction.ConstraintSatisfactoryProblem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuProblem extends ConstraintSatisfactoryProblem<Integer> {

    private int[][] notsolvedSudokuChart;
    private int[][] solvedSudokuChart = null;

    public SudokuProblem(String name, List<CSPVariable<Integer>> variables, List<CSPConstraint<Integer>> constraints, int[][] sudokuChart) {
        super(name, variables, constraints);
        notsolvedSudokuChart = sudokuChart;
    }

    public void addCurrentVariablesValuesAsSolution() {
        Map<CSPVariable<Integer>, Integer> solution = new HashMap<>();
        for (CSPVariable<Integer> variable : variables) {
            Integer value = variable.getVariableValue();
            solution.put(variable, value);
        }
        solutions.add(solution);
    }

    public void printProblem() {
        printNotSolvedChart();
        System.out.println();
        printSolutions();
    }

    private void printNotSolvedChart(){
        System.out.println("NOT SOLVED CHART:");
        for (int[] row : notsolvedSudokuChart) {
            for (int digit : row) {
                if (digit == 0) {
                    System.out.print("_");
                }else {
                    System.out.print(digit);
                }
            }
            System.out.println();
        }
    }

    private void printSolutions() {
        int solutionNumber = 0;
        for (Map<CSPVariable<Integer>, Integer> solution : solutions) {
            fillPuzzleChartWithSolution(solution);
            printSolution(++solutionNumber);
        }
    }


    public void fillPuzzleChartWithSolution(Map<CSPVariable<Integer>, Integer> solution) {
        solvedSudokuChart = new int[9][9];
        putVariableValuesIntoSolvedChart(solution);
    }

    private void putVariableValuesIntoSolvedChart(Map<CSPVariable<Integer>, Integer> solution) {
        for (CSPVariable<Integer> variable : solution.keySet()) {
            putVariableValueIntoSolvedChart((SudokuVariable) variable, solution.get(variable));
        }
    }

    private void putVariableValueIntoSolvedChart(SudokuVariable variable, Integer digit) {
        int row_i = variable.getRowNumber();
        int col_i = variable.getColumnNumber();
        solvedSudokuChart[row_i][col_i] = digit;
    }


    private void printSolution(int solutionNumber){
        System.out.println("SOLUTION " + solutionNumber + ":");
        if (solvedSudokuChart != null) {
            for (int[] row : solvedSudokuChart) {
                for (int digit : row) {
                    System.out.print(digit);
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
