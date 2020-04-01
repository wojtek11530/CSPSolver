package main;

import abstraction.ConstraintsNumberOrderSetter;
import abstraction.DomainSizeVariableOrderSetter;
import abstraction.TheSameDomainOrderSetter;
import crossword.CrosswordProblem;
import loaders.CrosswordProblemLoader;
import loaders.SudokuProblemLoader;
import solvers.MultiSolutionBacktrackingAlgorithm;
import solvers.MultiSolutionForwardChecking;
import sudoku.SudokuProblem;

import java.util.Scanner;

public class Main {

    public static final String PROBLEM_NAME = "crossword_4";

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        runProblem(PROBLEM_NAME);
        //printWelcomeMessage();
        //communicateWithUser();
    }

    private static void printWelcomeMessage() {
        System.out.println("Hello!");
        System.out.println("I only can solve sudoku and crossword problems.");
        System.out.println(" - To solve crossword problem type crossword_[number}");
        System.out.println("   e.g. crossword_0");
        System.out.println("   Crossword problem numbers are from 0 to 4");
        System.out.println(" - To solve sudoku problem type sudoku_[number}");
        System.out.println("   e.g. sudoku_1");
        System.out.println("   Sudoku problem numbers are from 1 to 46");
        System.out.println(" - To close type exit\n");
    }

    private static void communicateWithUser() {
        boolean closeProgram = false;
        while (!closeProgram) {
            System.out.print("User's input: ");
            String input = scanner.nextLine();
            String puzzleKind = input.split("_")[0];
            if (input.equals("exit")) {
                System.out.println("Program closing");
                closeProgram = true;
            } else if (puzzleKind.equals("sudoku") || puzzleKind.equals("crossword")) {
                System.out.println();
                runProblem(input);
            } else {
                System.out.println("Incorrect input! Try again.");
            }
        }
    }


    private static void runProblem(String problemName) {
        String problemKind = problemName.split("_")[0];
        int problemNumber = Integer.parseInt(problemName.split("_")[1]);
        if (problemKind.equals("crossword")) {
            crosswordRun(problemNumber);
        } else if (problemKind.equals("sudoku")) {
            sudokuRun(problemNumber);
        }
    }

    private static void crosswordRun(int problemNumber) {
        CrosswordProblem crosswordProblem = CrosswordProblemLoader.loadProblem(problemNumber);
        if (crosswordProblem != null) {
            MultiSolutionBacktrackingAlgorithm<String> backtrackAlgorithm = new MultiSolutionBacktrackingAlgorithm<>();
            MultiSolutionForwardChecking<String> forwardAlgorithm = new MultiSolutionForwardChecking<>();

            boolean solved = backtrackAlgorithm.solve(crosswordProblem,
                    new ConstraintsNumberOrderSetter<>(),
                    new TheSameDomainOrderSetter<>());
            if (!solved) {
                System.out.println("Problem is not solved");
            }
            crosswordProblem.printProblem();
            crosswordProblem.deleteAllSolutions();
            crosswordProblem.resetVariablesValues();
            solved = forwardAlgorithm.solve(crosswordProblem,
                    new ConstraintsNumberOrderSetter<>(),
                    new TheSameDomainOrderSetter<>());

            if (!solved) {
                System.out.println("Problem is not solved");
            }
            crosswordProblem.printProblem();
        }
    }

    private static void sudokuRun(int problemNumber) {
        SudokuProblem sudokuProblem = SudokuProblemLoader.loadProblem(problemNumber);

        if (sudokuProblem != null) {
            MultiSolutionBacktrackingAlgorithm<Integer> backtrackAlgorithm = new MultiSolutionBacktrackingAlgorithm<>();
            MultiSolutionForwardChecking<Integer> forwardAlgorithm = new MultiSolutionForwardChecking<>();

            boolean solved = backtrackAlgorithm.solve(sudokuProblem,
                    new DomainSizeVariableOrderSetter<>(),
                    new TheSameDomainOrderSetter<>());

            if (!solved) {
                System.out.println("Problem is not solved");
            }
            sudokuProblem.printProblem();
            sudokuProblem.deleteAllSolutions();
            sudokuProblem.resetVariablesValues();

            solved = forwardAlgorithm.solve(sudokuProblem,
                    new DomainSizeVariableOrderSetter<>(),
                    new TheSameDomainOrderSetter<>());

            if (!solved) {
                System.out.println("Problem is not solved");
            }
            sudokuProblem.printProblem();
        }
    }
}
