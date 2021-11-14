package tung.daongoc.shoppingcart.service;

import java.io.IOException;
import java.util.List;

public interface IService<T> {
    List<T> getAll();

    T getByID(Long id);

    void add(T object) throws IOException;
}
