package ru.msu.video_hosting.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.model.Client;
import org.hibernate.Session;

import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client, Integer> implements ClientDAO {

    public ClientDAOImpl() {
        super(Client.class);
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
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c JOIN History h ON c.clientId = h.clientId.clientId WHERE h.filmCopyId = :filmId", Client.class);
            query.setParameter("filmId", filmCopyId);
            return query.getResultList();
        }
    }

    @Override
    public List<Client> findClientsWithOverdueRentals() {
        Date currentDate = new Date();
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Client> query = session.createQuery("SELECT DISTINCT c FROM Client c JOIN History h ON c.clientId = h.clientId.clientId WHERE h.dateOfReturn < :currentDate", Client.class);
            query.setParameter("currentDate", currentDate);
            return query.getResultList();
        }
    }

    @Override
    public void updateClientInfo(Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(client);
            session.getTransaction().commit();
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
