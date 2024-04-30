package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.ClientDAOImpl;
import ru.msu.video_hosting.model.Client;

import java.util.List;

public class ClientService extends CommonService<Client, ClientDAOImpl> {
    public ClientService() {
        super(new ClientDAOImpl());
    }

    public List<Client> findByAddress(String address) {
        return dao.findByAddress(address);
    }

    public List<Client> findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
    }

    public boolean authenticate(String email, String password) {
        return dao.authenticate(email, password);
    }

    public List<Client> findClientsRentingFilm(int filmId) {
        return dao.findClientsRentingFilm(filmId);
    }

    public List<Client> findClientsWithOverdueRentals() {
        return dao.findClientsWithOverdueRentals();
    }

    public int countClients() {
        return dao.countClients();
    }
}
