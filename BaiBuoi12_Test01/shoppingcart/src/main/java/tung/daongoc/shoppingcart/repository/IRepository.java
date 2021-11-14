package tung.daongoc.shoppingcart.repository;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();

    T getByID(Long id);

    void add(T object);
}
