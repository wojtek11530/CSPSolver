package abstraction;

import java.util.List;

public interface VariablesOrderSetter<T> {
    void setOrder(List<CSPVariable<T>> variables);
}
