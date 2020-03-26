package abstraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Domain<T> {

    private List<T> domainValues = new ArrayList<T>();

    public List<T> getDomainValues() {
        return domainValues;
    }

    public T get(int index) {
        return domainValues.get(index);
    }

    public void setDomainValues(List<T> objectLists) {
        domainValues = objectLists;
    }

    public boolean contains(T object) {
        return domainValues.contains(object);
    }

    public void addToDomain(T object) {
        domainValues.add(object);
    }

    public void addToDomain(int index, T object) {
        domainValues.add(index, object);
    }

    public void removeFromDomain(T object) {
        domainValues.remove(object);
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
