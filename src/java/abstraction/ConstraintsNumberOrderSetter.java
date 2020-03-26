package abstraction;

import java.util.Comparator;
import java.util.List;

public class ConstraintsNumberOrderSetter<T> implements VariablesOrderSetter<T> {
    @Override
    public void setOrder(List<CSPVariable<T>> variables) {
        variables.sort(new Comparator<CSPVariable<T>>() {
            public int compare(CSPVariable<T> v1, CSPVariable<T> v2) {
                return Integer.compare(v2.getConstraintsNumber(), v1.getConstraintsNumber());
            }
        });
    }
}
