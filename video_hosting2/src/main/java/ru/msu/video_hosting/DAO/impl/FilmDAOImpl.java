package ru.msu.video_hosting.DAO.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.msu.video_hosting.DAO.FilmDAO;
import ru.msu.video_hosting.model.Film;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.FilmGenre;
import ru.msu.video_hosting.model.StorageInfo;

import java.util.List;

@Repository
public class FilmDAOImpl extends CommonDAOImpl<Film, Integer> implements FilmDAO {

    public FilmDAOImpl() {
        super();
        setEntityClass(Film.class);
    }

    @Override
    public Film findByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f WHERE f.filmTitle = :title", Film.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }
    }

    @Override
    public List<Film> findByGenre(String genre) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f WHERE f.filmGenre = :genre", Film.class);
            query.setParameter("genre", genre);
            return query.getResultList();
        }
    }

    @Override
    public List<Film> findByYearOfPremiere(int year) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f WHERE f.yearOfPremiere = :year", Film.class);
            query.setParameter("year", year);
            return query.getResultList();
        }
    }

    @Override
    public List<Film> findByCompany(String company) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f WHERE f.filmCompany = :company", Film.class);
            query.setParameter("company", company);
            return query.getResultList();
        }
    }

    @Override
    public List<Film> findByDirector(String director) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f WHERE f.director = :director", Film.class);
            query.setParameter("director", director);
            return query.getResultList();
        }
    }

    @Override
    public List<Film> findAllOrderByYearOfPremiere() {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Film> query = session.createQuery("SELECT f FROM Film f ORDER BY f.yearOfPremiere", Film.class);
            return query.getResultList();
        }
    }

    @Override
    @Transactional
    public void delete(Film entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<StorageInfo> storageInfos = session.createQuery("FROM StorageInfo WHERE filmId = :film", StorageInfo.class)
                    .setParameter("film", entity)
                    .getResultList();
            for (StorageInfo storageInfo : storageInfos) {
                session.delete(storageInfo);
            }
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

}
