package ru.msu.video_hosting.DAO.impl;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.msu.video_hosting.DAO.CommonDAO;
import ru.msu.video_hosting.model.CommonEntity;

import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
public class CommonDAOImpl<T extends CommonEntity<ID>, ID> implements CommonDAO<T, ID> {
    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> entityClass;

    public void setEntityClass(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void saveCollection(Collection<T> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (T entity : entities) {
                this.save(entity);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public T getById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, (Serializable) id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.from(entityClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}