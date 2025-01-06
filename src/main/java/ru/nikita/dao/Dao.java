package ru.nikita.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    Long save(E entity);

    List<E> findAll();

    Optional<E> findById(K id);

    boolean update(E entity);

    boolean delete(K id);
}
