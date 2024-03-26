package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.CommonEntity;

import java.util.Collection;
import java.util.List;

public interface CommonDAO<T extends CommonEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    T findById(ID id);

    List<T> findAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}