package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.Client;

import java.util.List;

public interface ClientDAO extends CommonDAO<Client, Integer> {
    List<Client> findByAddress(String address);

    List<Client> findByPhoneNumber(String phoneNumber);

    boolean authenticate(String email, String password);

    List<Client> findClientsRentingFilm(int filmId);

    List<Client> findClientsWithOverdueRentals();

    void updateClientInfo(Client client);

    int countClients();
}