package loaders;

import abstraction.CSPVariable;
import abstraction.Domain;
import sudoku.SudokuConstraint;
import sudoku.SudokuProblem;
import sudoku.SudokuVariable;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class SudokuProblemLoader extends Loader {

    public static final int MIN_PROBLEM_NUMBER = 1;
    public static final int MAX_PROBLEM_NUMBER = 46;

    public static final int SUDOKU_ROW_NUM = 9;
    public static final int SUDOKU_COL_NUM = 9;

    public static final String FILEPATH = "src\\data\\Sudoku.csv";

    public static SudokuProblem loadProblem(int problemNumber) {
        if (problemNumber < MIN_PROBLEM_NUMBER || problemNumber > MAX_PROBLEM_NUMBER) {
            System.out.println("There is no sudoku problem with number " + problemNumber);
            return null;
        } else {
            String name = "SUDOKU " + problemNumber;
            int[][] sudokuChart = getSudokuChart(problemNumber);
            List<SudokuVariable> sudokuVariables = getSudokuVariables(sudokuChart);
            addConstraintsToVariables(sudokuVariables);

            List<CSPVariable<Integer>> variables = new ArrayList<CSPVariable<Integer>>(sudokuVariables);
            return new SudokuProblem(name, variables, sudokuChart);
        }
    }

    private static int[][] getSudokuChart(int problemNumber) {
        int[][] puzzleArray = null;

        BufferedReader bufferedReader = getBufferedReader(FILEPATH);
        List<String> rows = readLinesFromBufferReader(bufferedReader);
        rows.remove(0);
        for (String row : rows) {
            String[] data = row.split(";");
            if (Integer.parseInt(data[0]) == problemNumber) {
                String puzzle = data[2];
                puzzleArray = readPuzzleChartFrom(puzzle);
            }
        }
        return puzzleArray;
    }

    private static List<SudokuVariable> getSudokuVariables(int[][] sudokuChart) {
        List<SudokuVariable> sudokuVariables = new ArrayList<SudokuVariable>();
        for (int row_i = 0; row_i < SUDOKU_ROW_NUM; row_i++) {
            for (int col_i = 0; col_i < SUDOKU_COL_NUM; col_i++) {
                int digit = sudokuChart[row_i][col_i];
                SudokuVariable sudokuVariable = getSudokuVariable(row_i, col_i, digit);
                sudokuVariables.add(sudokuVariable);
            }
        }
        return sudokuVariables;
    }

    private static SudokuVariable getSudokuVariable(int row_i, int col_i, int digit) {
        SudokuVariable sudokuVariable = new SudokuVariable(row_i, col_i);
        Domain<Integer> sudokuDomain = new Domain<Integer>();
        if (digit == 0) {
            for (int i = 1; i < 10; i++) {
                sudokuDomain.addToDomain(i);
            }
        } else {
            sudokuDomain.addToDomain(digit);
        }
        sudokuVariable.setDomain(sudokuDomain);
        return sudokuVariable;
    }

    private static void addConstraintsToVariables(List<SudokuVariable> sudokuVariables) {

        List<List<SudokuVariable>> variablesInOneRow = new ArrayList<List<SudokuVariable>>();
        List<List<SudokuVariable>> variablesInOneColumn = new ArrayList<List<SudokuVariable>>();
        List<List<SudokuVariable>> variablesInOneBox = new ArrayList<List<SudokuVariable>>();

        for (int i = 0; i < 9; i++) {
            variablesInOneRow.add(new ArrayList<SudokuVariable>());
            variablesInOneColumn.add(new ArrayList<SudokuVariable>());
            variablesInOneBox.add(new ArrayList<SudokuVariable>());
        }
        for (SudokuVariable variable : sudokuVariables) {
            int column = variable.getColumnNumber();
            int row = variable.getRowNumber();
            int box_num = row/3 * 3 +  column/3;

            variablesInOneRow.get(row).add(variable);
            variablesInOneColumn.get(column).add(variable);
            variablesInOneBox.get(box_num).add(variable);
        }
        for (List<SudokuVariable> variables : variablesInOneRow) {
            SudokuConstraint constraint =  new SudokuConstraint(variables);
            //constraintList.add(constraint);
            for (SudokuVariable variable : variables) {
                variable.addConstraint(constraint);
            }
        }
        for (List<SudokuVariable> variables : variablesInOneColumn) {
            SudokuConstraint constraint =  new SudokuConstraint(variables);
            //constraintList.add(constraint);
            for (SudokuVariable variable : variables) {
                variable.addConstraint(constraint);
            }
        }
        for (List<SudokuVariable> variables : variablesInOneBox) {
            SudokuConstraint constraint =  new SudokuConstraint(variables);
            //constraintList.add(constraint);
            for (SudokuVariable variable : variables) {
                variable.addConstraint(constraint);
            }
        }
    }


    private static int[][] readPuzzleChartFrom(String puzzle) {
        String[] puzzleChars = puzzle.split("");
        int[][] puzzleChart = new int[SUDOKU_ROW_NUM][SUDOKU_COL_NUM];
        for (int row_i = 0; row_i < SUDOKU_ROW_NUM; row_i++) {
            for (int col_i = 0; col_i < SUDOKU_COL_NUM; col_i++) {
                String currentCharacter = puzzleChars[row_i*SUDOKU_COL_NUM + col_i];
                if (currentCharacter.equals(".")){
                    puzzleChart[row_i][col_i] = 0;
                } else {
                    puzzleChart[row_i][col_i] = Integer.parseInt(currentCharacter);
                }
            }
        }
        return puzzleChart;
    }


}
