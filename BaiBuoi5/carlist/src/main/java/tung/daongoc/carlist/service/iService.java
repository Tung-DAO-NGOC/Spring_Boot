package tung.daongoc.carlist.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface iService<T> {
    List<T> getAll();

    T getByID(int ID);

    void updateByID(T newObject, int ID);

    void deleteByID(int ID);

    List<T> search(String keyword, String sort);

    void add(T newObject);
}
