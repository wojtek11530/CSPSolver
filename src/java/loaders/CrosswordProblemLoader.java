package loaders;

import abstraction.CSPVariable;
import abstraction.Domain;
import crossword.*;

import java.io.*;
import java.util.*;


public class CrosswordProblemLoader extends Loader {

    public static final int MIN_PROBLEM_NUMBER = 0;
    public static final int MAX_PROBLEM_NUMBER = 4;
    public static final String PATH = "src\\data\\Jolka\\";

    public static CrosswordProblem loadProblem(int problemNumber) {
        if (problemNumber < MIN_PROBLEM_NUMBER || problemNumber > MAX_PROBLEM_NUMBER) {
            System.out.println("There is no crossword problem with number " + problemNumber);
            return null;
        } else {
            String name = "CROSSWORD " + problemNumber;

            List<String> allDomainWords = getWords(problemNumber);
            char[][] puzzleChart = getPuzzleChart(problemNumber);
            List<CrosswordVariable> crosswordVariables = getVariablesFor(puzzleChart);
            addConstraintToVariables(crosswordVariables);
            setDomainsForVariables(crosswordVariables, allDomainWords);

            List<CSPVariable<String>> variables = new ArrayList<CSPVariable<String>>(crosswordVariables);

            return new CrosswordProblem(name, variables, puzzleChart);
        }
    }


    private static List<String> getWords(int problemNumber) {
        String filename = "words" + problemNumber;
        BufferedReader bufferedReader = getBufferedReader(PATH + filename);
        List<String> words = readLinesFromBufferReader(bufferedReader);
        return words;
    }

    private static char[][] getPuzzleChart(int problemNumber) {
        String filename = "puzzle" + problemNumber;
        BufferedReader bufferedReader = getBufferedReader(PATH + filename);
        char[][] puzzleArray = readPuzzleChartFrom(bufferedReader);
        return puzzleArray;
    }

    private static List<CrosswordVariable> getVariablesFor(char[][] puzzleArray) {
        List<CrosswordVariable> variables = new ArrayList<CrosswordVariable>();
        readRowVariables(puzzleArray, variables);
        readColumnVariables(puzzleArray, variables);
        return variables;
    }

    private static void addConstraintToVariables(List<CrosswordVariable> variables) {
        addIntersectionConstraints(variables);
        addRepeatConstraints(variables);
    }

    private static void addIntersectionConstraints(List<CrosswordVariable> variables) {
        for (CrosswordVariable variable : variables) {
            if (variable.getDirection() == Direction.ROW) {
                addIntersectionConstraintsForRowVariable(variable, variables);
            }
        }
    }

    private static void addRepeatConstraints(List<CrosswordVariable> variables) {
        for (int i = 0; i < variables.size(); i++) {
            for (int j = i + 1; j < variables.size(); j++) {
                CrosswordVariable variable1 = variables.get(i);
                CrosswordVariable variable2 = variables.get(j);
                if (variable1 != variable2) {
                    CrosswordRepeatConstraint constraint = new CrosswordRepeatConstraint(variable1, variable2);
                    variable1.addConstraint(constraint);
                    variable2.addConstraint(constraint);
                }
            }
        }
    }

    private static void setDomainsForVariables(List<CrosswordVariable> crosswordVariables, List<String> allDomainWords) {
        Map<Integer, List<String>> differentLengthWordLists = getDifferentLengthWordLists(allDomainWords);
        for (CrosswordVariable variable : crosswordVariables) {
            int length = variable.getLength();
            List<String> wordList = differentLengthWordLists.get(length);

            Domain<String> variableDomain = new Domain<String>();
            variableDomain.setDomainValues(new ArrayList<String>(wordList));
            variable.setDomain(variableDomain);
        }
    }

    private static Map<Integer, List<String>> getDifferentLengthWordLists(List<String> allDomainWords) {
        Map<Integer, List<String>> differentWordsLengthDomains = new HashMap<Integer, List<String>>();
        for (String word : allDomainWords) {
            int length = word.length();
            if (differentWordsLengthDomains.containsKey(length)) {
                differentWordsLengthDomains.get(length).add(word);
            } else {
                List<String> list = new ArrayList<String>();
                list.add(word);
                differentWordsLengthDomains.put(length, list);
            }
        }
        return differentWordsLengthDomains;
    }


    private static char[][] readPuzzleChartFrom(BufferedReader br) {
        List<String> rows = readLinesFromBufferReader(br);
        char[][] puzzleArray = createPuzzleArrayFrom(rows);
        return puzzleArray;
    }

    private static void readRowVariables(char[][] puzzleArray, List<CrosswordVariable> variables) {
        for (int row_i = 0; row_i < puzzleArray.length; row_i++) {
            char[] rowCharacters = puzzleArray[row_i];
            readVariablesFromRow(variables, row_i, rowCharacters);
        }
    }

    private static void readColumnVariables(char[][] puzzleArray, List<CrosswordVariable> variables) {
        for (int column_i = 0; column_i < puzzleArray[0].length; column_i++) {
            char[] columnCharacters = new char[puzzleArray.length];
            for (int row_i = 0; row_i < puzzleArray.length; row_i++) {
                columnCharacters[row_i] = puzzleArray[row_i][column_i];
            }
            readVariablesFromColumn(variables, column_i, columnCharacters);
        }
    }

    private static void addIntersectionConstraintsForRowVariable(CrosswordVariable rowVariable,
                                                                 List<CrosswordVariable> variables) {
        for (CrosswordVariable otherVariable : variables) {
            if (otherVariable.getDirection() == Direction.COLUMN && checkIfIntersect(rowVariable, otherVariable)) {
                CrosswordIntersectionConstraint constraint = new CrosswordIntersectionConstraint(rowVariable, otherVariable);
                rowVariable.addConstraint(constraint);
                otherVariable.addConstraint(constraint);
            }
        }
    }


    private static char[][] createPuzzleArrayFrom(List<String> rows) {
        char[][] puzzleArray = new char[rows.size()][];
        for (int row_i = 0; row_i < rows.size(); row_i++) {
            String row = rows.get(row_i);
            char[] characters = row.toCharArray();
            puzzleArray[row_i] = characters;
        }
        return puzzleArray;
    }

    private static void readVariablesFromRow(List<CrosswordVariable> variables, int row_i, char[] rowCharacters) {
        Integer beginningColumn = null;
        int fieldLength = 0;
        for (int column_i = 0; column_i < rowCharacters.length; column_i++) {
            char character = rowCharacters[column_i];
            if (character == '_') {
                if (beginningColumn == null) {
                    beginningColumn = column_i;
                }
                fieldLength++;
            } else {
                if (fieldLength >= 2) {
                    variables.add(new CrosswordVariable(Direction.ROW, fieldLength, row_i, beginningColumn));
                }
                fieldLength = 0;
                beginningColumn = null;
            }
        }
        if (fieldLength >= 2) {
            variables.add(new CrosswordVariable(Direction.ROW, fieldLength, row_i, beginningColumn));
        }
    }

    private static void readVariablesFromColumn(List<CrosswordVariable> variables,
                                                int column_i, char[] columnCharacters) {
        Integer beginningRow = null;
        int fieldLength = 0;
        for (int row_i = 0; row_i < columnCharacters.length; row_i++) {
            char character = columnCharacters[row_i];
            if (character == '_') {
                if (beginningRow == null) {
                    beginningRow = row_i;
                }
                fieldLength++;
            } else {
                if (fieldLength >= 2) {
                    variables.add(new CrosswordVariable(Direction.COLUMN, fieldLength, beginningRow, column_i));
                }
                fieldLength = 0;
                beginningRow = null;
            }
        }
        if (fieldLength >= 2) {
            variables.add(new CrosswordVariable(Direction.COLUMN, fieldLength, beginningRow, column_i));
        }
    }

    private static boolean checkIfIntersect(CrosswordVariable rowVariable, CrosswordVariable columnVariable) {
        int rowNumOfRowVariable = rowVariable.getBeginningRowNumber();
        int beginningColumnNumOfRowVariable = rowVariable.getBeginningColumnNumber();
        int lengthRowOfVariable = rowVariable.getLength();
        int beginningRowNumOfColumnVariable = columnVariable.getBeginningRowNumber();
        int columnNumOfColumnVariable = columnVariable.getBeginningColumnNumber();
        int lengthOfColumnVariable = columnVariable.getLength();

        boolean rowIntersecting = beginningRowNumOfColumnVariable <= rowNumOfRowVariable &&
                rowNumOfRowVariable < beginningRowNumOfColumnVariable + lengthOfColumnVariable;
        boolean columnIntersecting = beginningColumnNumOfRowVariable <= columnNumOfColumnVariable &&
                columnNumOfColumnVariable < beginningColumnNumOfRowVariable + lengthRowOfVariable;
        return rowIntersecting && columnIntersecting;
    }

}
