package ru.msu.video_hosting.DAO.impl;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.CommonDAO;
import ru.msu.video_hosting.model.CommonEntity;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class CommonDAOImpl<T extends CommonEntity<ID>, ID> implements CommonDAO<T, ID> {

    protected SessionFactory sessionFactory;

    private final Class<T> entityClass;

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }


    public CommonDAOImpl(Class<T> entityClass) {
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
    public void deleteById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = getById(id);
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
            return session.get(entityClass, id);
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