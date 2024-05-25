package ru.msu.video_hosting.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.HistoryDAO;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
public class HistoryDAOImpl extends CommonDAOImpl<History, Integer> implements HistoryDAO {
    @Autowired
    public HistoryDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        setEntityClass(History.class);
    }

    @Override
    public List<History> findByClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Integer client_id = client.getClientId();
            TypedQuery<History> query = session.createQuery("SELECT h FROM History h WHERE h.clientId = :client_id", History.class);
            query.setParameter("client_id", client_id);
            return query.getResultList();
        }
    }

    @Override
    public List<History> findByFilmCopy(FilmCopies filmCopy) {
        try (Session session = sessionFactory.openSession()) {
            Integer filmCopy_id = filmCopy.getFilmCopiesId();
            TypedQuery<History> query = session.createQuery("SELECT h FROM History h WHERE h.filmCopyId = :filmCopy_id", History.class);
            query.setParameter("filmCopy_id", filmCopy_id);
            return query.getResultList();
        }
    }

}
