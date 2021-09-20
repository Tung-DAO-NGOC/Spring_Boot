package tung.daongoc.bookstoreweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T, CT> {

    // CRUD
    /**
     * Create an object
     * @param The input object
     */
    T create(CT object);

    /**
     * Read all object
     * @return The list of object
     */
    List<T> readAll();

    /**
     * Find object based on keywords
     * @param keywords The keyword to find
     * @return List of object contain keyword at title.
     */
    List<T> readByKeyword(String keywords);

    /**
     * Read single object
     * @param id The id of Object
     * @return Object
     */
    Optional<T> readByID(int id);

    /**
     * Delete an object based on its ID
     * @param id The id of deleted object
     * @return The object list after deleting object
     */
    List<T> deleteByID(int id);
}
