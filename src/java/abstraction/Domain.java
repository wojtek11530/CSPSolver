package abstraction;

import java.util.*;

public class Domain<T> {

    private List<T> domainValues = new ArrayList<>();

    public List<T> getDomainValues() {
        return domainValues;
    }

    public void setDomainValues(List<T> objectLists) {
        domainValues = objectLists;
    }

    public boolean isEmpty() { return domainValues.isEmpty(); }

    public T get(int index) {
        return domainValues.get(index);
    }

    public boolean contains(T object) {
        return domainValues.contains(object);
    }

    public void addToDomain(T object) {
        domainValues.add(object);
    }

    public void addToDomain(Set<T> objects) {
        domainValues.addAll(objects);
    }

    public void addToDomain(int index, T object) {
        domainValues.add(index, object);
    }

    public void removeFromDomain(T object) {
        domainValues.remove(object);
    }

    public void removeFromDomain(Set<T> objects) {
        domainValues.removeAll(objects);
    }

    public int getSize() {
        return domainValues.size();
    }

    public void shuffle() {
        Collections.shuffle(domainValues);
    }

    public Iterator<T> iterator() {
        return domainValues.iterator();
    }
}
