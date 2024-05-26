package ru.msu.video_hosting.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.StorageInfoDAO;
import ru.msu.video_hosting.model.StorageInfo;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
public class StorageInfoDAOImpl extends CommonDAOImpl<StorageInfo, Integer> implements StorageInfoDAO {

    @Autowired
    public StorageInfoDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        setEntityClass(StorageInfo.class);
    }

    @Override
    public List<StorageInfo> findByFilmId(String filmId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<StorageInfo> query = session.createQuery("SELECT si FROM StorageInfo si WHERE si.filmId.id = :filmId", StorageInfo.class);
            query.setParameter("filmId", filmId);
            return query.getResultList();
        }
    }

    @Override
    public List<StorageInfo> findByDeviceType(String deviceType) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<StorageInfo> query = session.createQuery("SELECT si FROM StorageInfo si WHERE si.storageDeviceType = :deviceType", StorageInfo.class);
            query.setParameter("deviceType", deviceType);
            return query.getResultList();
        }
    }

    @Override
    public List<StorageInfo> findAllByFilmTitle(String filmTitle) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<StorageInfo> query = session.createQuery("SELECT si FROM StorageInfo si WHERE si.filmId.filmTitle = :filmTitle", StorageInfo.class);
            query.setParameter("filmTitle", filmTitle);
            return query.getResultList();
        }
    }

    @Override
    public int getCountByFilmAndDevice(int filmId, String deviceType) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Long> query = session.createQuery("SELECT COUNT(si) FROM StorageInfo si WHERE si.filmId.id = :filmId AND si.storageDeviceType = :deviceType", Long.class);
            query.setParameter("filmId", filmId);
            query.setParameter("deviceType", deviceType);
            return query.getSingleResult().intValue();
        }
    }

    @Override
    public int getTotalAvailableCopiesForFilm(int filmId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Long> query = session.createQuery("SELECT SUM(si.fullAmount) FROM StorageInfo si WHERE si.filmId.id = :filmId", Long.class);
            query.setParameter("filmId", filmId);
            Long totalCopies = query.getSingleResult();
            return totalCopies != null ? totalCopies.intValue() : 0;
        }
    }

    @Override
    public int getRemainingCopiesForFilm(int filmId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Long> query = session.createQuery("SELECT SUM(si.freeAmount) FROM StorageInfo si WHERE si.filmId.id = :filmId", Long.class);
            query.setParameter("filmId", filmId);
            Long remainingCopies = query.getSingleResult();
            return remainingCopies != null ? remainingCopies.intValue() : 0;
        }
    }

    public StorageInfo findByCopyId(int copyId){
        try (Session session = sessionFactory.openSession()) {
            for(StorageInfo si: getAll()){
                if(List.of(si.getFilmCopies()).contains(copyId))
                    return si;

            }
            throw new RuntimeException("can't find StorageInfo that contains this copy");
        }
    }

}