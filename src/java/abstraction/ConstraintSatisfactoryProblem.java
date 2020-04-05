package abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ConstraintSatisfactoryProblem<T> {

    protected String name;
    protected List<CSPVariable<T>> variables;
    protected List<Map<CSPVariable<T>,T>> solutions = new ArrayList<>();
    protected List<CSPConstraint<T>> constraints;

    public ConstraintSatisfactoryProblem(String name, List<CSPVariable<T>> variables, List<CSPConstraint<T>> constraints) {
        this.name = name;
        this.variables = variables;
        this.constraints = constraints;
    }

    public List<CSPVariable<T>> getVariables() {
        return variables;
    }

    public String getName() {
        return name;
    }

    public abstract void addCurrentVariablesValuesAsSolution();

    public void deleteAllSolutions() {
        solutions.clear();
    }

    public void resetVariablesValues() {
        for (CSPVariable<T>  variable: variables) {
            variable.setEmpty();
        }
    }

    public abstract void printProblem();

}


