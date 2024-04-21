package carsharing.DAO;

import java.util.List;
import java.util.Optional;

public interface BaseDAO <E> {
    Optional<E> getByName(String name);
    Optional<E> getById(int id);
    List<E> getAll();
    void save(E entity);
    void update(E entity);
    void delete(E entity);
}
