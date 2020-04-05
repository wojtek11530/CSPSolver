package solvers;

import java.time.Duration;
import java.time.LocalTime;

public class SolvingAlgorithmStatistics {

    private String name;
    private LocalTime beginningTime;
    private long overallTime;
    private int overallNodesNumber = 0;
    private int overallBacktrackNumber = 0;
    private long timeToFirstSolution;
    private int nodesNumberToFirstSolution = 0;
    private int backtracksNumberToFirstSolution = 0;
    private int solutionsNumber = 0;

    public SolvingAlgorithmStatistics(String name) {
        this.name = name;
    }

    public void start() {
        beginningTime = LocalTime.now();
    }

    public void increaseSolutionsNumber() {
        solutionsNumber++;
    }

    public void increaseOverallNodesNumber() {
        overallNodesNumber++;
    }

    public void increaseOverallBacktrackNumber() {
        overallBacktrackNumber++;
    }

    public void setOverallTime() {
        LocalTime now = LocalTime.now();
        overallTime = Duration.between(beginningTime, now).toMillis();
    }

    public void setTimeToFirstSolution() {
        LocalTime now = LocalTime.now();
        timeToFirstSolution = Duration.between(beginningTime, now).toMillis();
    }

    public void setNodesNumberToFirstSolution() {
        nodesNumberToFirstSolution = overallNodesNumber;
    }

    public void setBacktracksNumberToFirstSolution() {
        backtracksNumberToFirstSolution = overallBacktrackNumber;
    }

    public int getSolutionsNumber() {
        return solutionsNumber;
    }

    public boolean isAnySolutionFound() {
        return solutionsNumber > 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.toUpperCase())
                .append(":\nSolutions number: ").append(solutionsNumber)
                .append("\nOverall time: ").append(overallTime).append("ms")
                .append("\nOverall nodes number: ").append(overallNodesNumber)
                .append("\nOverall backtrack number: ").append(overallBacktrackNumber);
        if (getSolutionsNumber() > 0) {
            stringBuilder
                    .append("\nTime to first solution: ").append(timeToFirstSolution).append("ms")
                    .append("\nNodes number to first solution: ").append(nodesNumberToFirstSolution)
                    .append("\nBacktracks number to first solution: ").append(backtracksNumberToFirstSolution)
                    .append("\n");
        } else {
            stringBuilder
                    .append("\nTime to first solution: -")
                    .append("\nNodes number to first solution: -")
                    .append("\nBacktracks number to first solution: -")
                    .append("\n");
        }
        return stringBuilder.toString();

    }
}
