package abstraction;

public interface CSPSolver<T> {
    boolean solve(ConstraintSatisfactoryProblem<T> problem,
                  VariablesOrderSetter<T> variablesOrderSetter,
                  DomainOrderSetter<T> domainOrderSetter);
}
