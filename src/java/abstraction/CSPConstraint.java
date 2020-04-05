package abstraction;

import java.util.Set;

public interface CSPConstraint<T> {
    boolean isSatisfied();
    boolean involvesVariable(CSPVariable<T> variable);
    Set<T> getValuesToFilter(CSPVariable<T> emptyVariable, CSPVariable<T> variableWithValue);
}
