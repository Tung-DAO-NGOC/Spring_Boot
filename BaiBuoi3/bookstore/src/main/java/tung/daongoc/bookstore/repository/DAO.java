package tung.daongoc.bookstore.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T> {
    protected List<T> listObject = new ArrayList<T>();

    public abstract List<T> getall();

    // Optional<T>: container object which may or may not contain a non-null value. 
    // If a value is present: isPresent() is true and get() will return the value
    public abstract Optional<T> get(int id);

    public abstract void add(T t);

    public abstract void update(T t);

    public abstract void deleteByID(int id);

    public abstract void delete(T t);
}
