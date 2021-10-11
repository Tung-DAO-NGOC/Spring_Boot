package tung.daongoc.carlist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {
    List<T> getAll();

    T getByID(int ID);

    void updateByID(T newObject, int ID);

    void deleteByID(int ID);

    List<T> search(String keyword, String sort);
}
