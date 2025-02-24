package dao;

import java.util.List;

public interface CrudOperations<E> {
    List<E> getAll(int offset, int limit);
    E findById(List<E> list, int E_id);
}
