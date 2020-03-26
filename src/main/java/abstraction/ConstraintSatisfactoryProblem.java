package abstraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConstraintSatisfactoryProblem<T> {

    protected String name;
    protected List<CSPVariable<T>> variables;
    protected List<Map<CSPVariable<T>,T>> solutions = new ArrayList<Map<CSPVariable<T>, T>>();

    public ConstraintSatisfactoryProblem(String name, List<CSPVariable<T>> variables) {
        this.name = name;
        this.variables = variables;
    }

    public List<CSPVariable<T>> getVariables() {
        return variables;
    }

    public String getName() {
        return name;
    }

    public abstract void addCurrentVariablesValuesAsSolution();
}


