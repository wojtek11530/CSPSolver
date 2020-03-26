package abstraction;

import java.util.List;

public class DomainSizeOrderSetter<T> implements VariablesOrderSetter<T> {
    @Override
    public void setOrder(List<CSPVariable<T>> variables) {
        variables.sort((v1, v2) -> Integer.compare(v1.getDomain().getSize(), v2.getDomain().getSize()));
    }
}
