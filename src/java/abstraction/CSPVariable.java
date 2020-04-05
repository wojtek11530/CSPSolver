package abstraction;

import java.util.List;
import java.util.Set;

public interface CSPVariable<T> {

    boolean checkConstraints();

    T getVariableValue();
    void setVariableValue(T value);

    boolean isEmpty();
    void setEmpty();

    void addConstraint(CSPConstraint<T> constraint);
    int getConstraintsNumber();

    Domain<T> getDomain();
    void setDomain(Domain<T> domain);

    Set<T> filterDomain(CSPVariable<T> otherVariable);
    Domain<T> getFilteredDomain();
    void setFilteredDomain(Domain<T> domain);
}
