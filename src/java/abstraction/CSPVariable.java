package abstraction;

public interface CSPVariable<T> {

    boolean checkConstraints();

    T getVariableValue();
    void setVariableValue(T value);

    void addConstraint(CSPConstraint constraint);
    int getConstraintsNumber();

    Domain<T> getDomain();
    void setDomain(Domain<T> domain);
}
