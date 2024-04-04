package ru.msu.video_hosting.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.HistoryDAO;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;
import ru.msu.video_hosting.model.HistoryPK;

import java.util.List;

@Repository
public class HistoryDAOImpl extends CommonDAOImpl<History, HistoryPK> implements HistoryDAO {

    public HistoryDAOImpl() {
        super(History.class);
    }

    @Override
    public List<History> findByClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<History> query = session.createQuery("SELECT h FROM History h WHERE h.clientId = :client", History.class);
            query.setParameter("client", client);
            return query.getResultList();
        }
    }

    @Override
    public List<History> findByFilmCopy(FilmCopies filmCopy) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<History> query = session.createQuery("SELECT h FROM History h WHERE h.filmCopyId = :filmCopy", History.class);
            query.setParameter("filmCopy", filmCopy);
            return query.getResultList();
        }
    }

}
