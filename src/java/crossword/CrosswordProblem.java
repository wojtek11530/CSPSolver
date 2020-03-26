package crossword;

import abstraction.CSPVariable;
import abstraction.ConstraintSatisfactoryProblem;

import java.util.*;

public class CrosswordProblem extends ConstraintSatisfactoryProblem<String> {

    private char[][] emptyPuzzleChart;
    private char[][] solvedPuzzleChart = null;

    public CrosswordProblem(String name, List<CSPVariable<String>> variables,
                            char[][] puzzleChart) {
        super(name, variables);
        this.emptyPuzzleChart = puzzleChart;
    }

    public void addCurrentVariablesValuesAsSolution() {
        Map<CSPVariable<String>, String> solution = new HashMap<CSPVariable<String>, String>();
        for (CSPVariable<String> variable : variables) {
            String value = variable.getVariableValue();
            solution.put(variable, value);
        }
        solutions.add(solution);
    }

    public void printCrosswordProblem() {
        printEmptyPuzzleChart();
        System.out.println();
        printDomainWords();
        System.out.println();
        printSolutions();

    }

    private void printEmptyPuzzleChart() {
        System.out.println("EMPTY CHART:");
        for (char[] charactersInRow : emptyPuzzleChart) {
            for (char character : charactersInRow) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private void printDomainWords() {
        Set<String> entireDomain = new HashSet<String>();
        for (CSPVariable<String> variable : variables) {
            entireDomain.addAll(variable.getDomain().getDomainValues());
        }
        System.out.println("DOMAIN WORDS: ");
        int i = 0;
        for (String word : entireDomain) {
            System.out.print(word);
            if (i + 1 < entireDomain.size()) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private void printSolutions() {
        int solutionNumber = 0;
        for (Map<CSPVariable<String>, String> solution : solutions) {
            fillPuzzleChartWithSolution(solution);
            printSolution(++solutionNumber);
        }
    }

    public void fillPuzzleChartWithSolution(Map<CSPVariable<String>, String> solution) {
        solvedPuzzleChart = new char[emptyPuzzleChart.length][emptyPuzzleChart[0].length];
        createSolvedChartFromEmptyChart();
        putVariableValuesIntoSolvedChart(solution);
    }


    private void printSolution(int solutionNumber) {
        System.out.println("SOLUTION " + solutionNumber + ":");
        for (char[] charactersInRow : solvedPuzzleChart) {
            for (char character : charactersInRow) {
                System.out.print(character);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void createSolvedChartFromEmptyChart() {
        for (int row_i = 0; row_i < emptyPuzzleChart.length; row_i++) {
            for (int col_i = 0; col_i < emptyPuzzleChart[0].length; col_i++) {
                solvedPuzzleChart[row_i][col_i] = emptyPuzzleChart[row_i][col_i];
            }
        }
    }

    private void putVariableValuesIntoSolvedChart(Map<CSPVariable<String>, String> solution) {
        for (CSPVariable<String> variable : solution.keySet()) {
            putVariableValueIntoSolvedChart((CrosswordVariable) variable, solution.get(variable));
        }
    }

    private void putVariableValueIntoSolvedChart(CrosswordVariable variable, String word) {
        int row_i = variable.getBeginningRowNumber();
        int col_i = variable.getBeginningColumnNumber();
        Direction direction = variable.getDirection();
        for (char character : word.toCharArray()) {
            solvedPuzzleChart[row_i][col_i] = character;
            if (direction == Direction.ROW) {
                col_i++;
            } else {
                row_i++;
            }
        }
    }
}
