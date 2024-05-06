package ru.msu.video_hosting.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.model.Client;
import org.hibernate.Session;

import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client, Integer> implements ClientDAO {

    public ClientDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        setEntityClass(Client.class);
    }

    @Override
    public List<Client> findByAddress(String address) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c WHERE c.address = :address", Client.class);
            query.setParameter("address", address);
            return query.getResultList();
        }
    }

    @Override
    public List<Client> findByPhoneNumber(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c WHERE c.phone_number = :phoneNumber", Client.class);
            query.setParameter("phoneNumber", phoneNumber);
            return query.getResultList();
        }
    }

    @Override
    public List<Client> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class);
            query.setParameter("email", email);
            return query.getResultList();
        }
    }

    @Override
    public boolean authenticate(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Long> query = session.createQuery("SELECT COUNT(c) FROM Client c WHERE c.email = :email AND c.password = :password", Long.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult() == 1;
        }
    }

    @Override
    public List<Client> findClientsRentingFilm(int filmCopyId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c JOIN History h ON c.clientId = h.clientId WHERE h.filmCopyId = :filmId", Client.class);
            query.setParameter("filmId", filmCopyId);
            return query.getResultList();
        }
    }

    @Override
    public List<Client> findClientsWithOverdueRentals() {
        LocalDate currentDate = LocalDate.now();
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery(
                    "SELECT DISTINCT c " +
                            "FROM Client c " +
                            "JOIN History h ON c.clientId = h.clientId " +
                            "JOIN FilmCopies fc ON h.filmCopyId = fc.filmCopiesId " +
                            "WHERE h.dateOfReturn < :currentDate AND fc.status = 'Выдан'",
                    Client.class
            );
            query.setParameter("currentDate", currentDate);
            return query.getResultList();
        }
    }

    @Override
    public int countClients() {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Long> query = session.createQuery("SELECT COUNT(c) FROM Client c", Long.class);
            return query.getSingleResult().intValue();
        }
    }

}
