package ru.msu.video_hosting.DAO.impl;

import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.model.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class ClientDAOImpl extends CommonDAOImpl<Client, Integer> implements ClientDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public ClientDAOImpl() {
        super(Client.class);
    }

    @Override
    public List<Client> findByAddress(String address) {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.address = :address", Client.class);
        query.setParameter("address", address);
        return query.getResultList();
    }

    @Override
    public List<Client> findByPhoneNumber(String phoneNumber) {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.phone_number = :phoneNumber", Client.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getResultList();
    }

    @Override
    public boolean authenticate(String email, String password) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Client c WHERE c.email = :email AND c.password = :password", Long.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult() == 1;
    }

    @Override
    public List<Client> findClientsRentingFilm(int filmCopyId) {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c JOIN History h ON c.clientId = h.clientId.clientId WHERE h.filmCopyId = :filmId", Client.class);
        query.setParameter("filmId", filmCopyId);
        return query.getResultList();
    }

    @Override
    public List<Client> findClientsWithOverdueRentals() {
        Date currentDate = new Date();
        TypedQuery<Client> query = entityManager.createQuery("SELECT DISTINCT c FROM Client c JOIN History h ON c.clientId = h.clientId.clientId WHERE h.dateOfReturn < :currentDate", Client.class);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    @Override
    public void updateClientInfo(Client client) {
        entityManager.merge(client);
    }

    @Override
    public int countClients() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Client c", Long.class);
        return query.getSingleResult().intValue();
    }

}
