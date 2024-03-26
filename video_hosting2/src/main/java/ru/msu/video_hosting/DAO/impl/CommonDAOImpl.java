package ru.msu.video_hosting.DAO.impl;
import ru.msu.video_hosting.DAO.CommonDAO;
import ru.msu.video_hosting.model.CommonEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public class CommonDAOImpl<T extends CommonEntity<ID>, ID> implements CommonDAO<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public CommonDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        T entityToRemove = entityManager.find(entityClass, id);
        if (entityToRemove != null) {
            entityManager.remove(entityToRemove);
        }
    }

    @Override
    @Transactional
    public void saveCollection(Collection<T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Override
    public T getById(ID id) {
        return findById(id);
    }

    @Override
    public List<T> getAll() {
        return findAll();
    }
}