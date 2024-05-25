package ru.msu.video_hosting.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.msu.video_hosting.DAO.impl.ClientDAOImpl;
import ru.msu.video_hosting.model.Client;

import java.util.List;
@Service
public class ClientService extends CommonService<Client, ClientDAOImpl> {
    @Autowired
    public ClientService(SessionFactory sessionFactory) {
        super(new ClientDAOImpl(sessionFactory));
    }

    public List<Client> findByAddress(String address) {
        return dao.findByAddress(address);
    }

    public List<Client> findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
    }
    public List<Client> findByEmail(String email) {
        return dao.findByEmail(email);
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
