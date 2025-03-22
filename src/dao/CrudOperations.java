package dao;

import java.util.List;

public interface CrudOperations<E> {

    List<E> saveAll(List<E> entities); // Create if entities not exists, update if entities exists
    List<E> getAll(int offset, int limit);
    E findById(long E_id);
    boolean deleteOperation(long id);
}
