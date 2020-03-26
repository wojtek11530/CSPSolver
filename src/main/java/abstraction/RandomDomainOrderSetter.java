package abstraction;

public class RandomDomainOrderSetter<T> implements DomainOrderSetter<T> {
    @Override
    public void setOrder(Domain<T> domain) {
        domain.shuffle();
    }
}
