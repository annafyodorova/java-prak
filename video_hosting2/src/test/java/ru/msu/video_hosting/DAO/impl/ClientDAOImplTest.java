package ru.msu.video_hosting.DAO.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.VideoHostongApplication;
import ru.msu.video_hosting.model.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = VideoHostongApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class ClientDAOImplTest {
    @Autowired
    private ClientDAO clientDAO;

    @Test
    void getFields() {
        Client client = clientDAO.getById(1);
        assertEquals(1, client.getId());
        assertEquals("anna", client.getFull_name());
        assertEquals("aaa2003@gmail.com", client.getEmail());
        assertEquals("89999999999", client.getPhone_number());
    }

    @Test
    void update() {
        String newName = "vvvvvvvv";
        String newEmail = "vvv@yandex.ru";
        String newPhone = "89990000000";

        Client updateClient = clientDAO.getById(10);
        updateClient.setFull_name(newName);
        updateClient.setEmail(newEmail);
        updateClient.setPhone_number(newPhone);
        clientDAO.update(updateClient);

        Client updatedClient = clientDAO.getById(10);
        assertEquals(newName, updatedClient.getFull_name());
        assertEquals(newEmail, updatedClient.getEmail());
        assertEquals(newPhone, updatedClient.getPhone_number());
    }

    @Test
    void delete() {
        Client deleteClient = clientDAO.getById(10);
        clientDAO.delete(deleteClient);

        Client deletedClient = clientDAO.getById(10);
        assertNull(deletedClient);

        Collection<Client> personListAll = clientDAO.getAll();
        assertEquals(9, personListAll.size());
    }

    @Test
    void findByAddress() {
        String address = "moscow, krasnokaz st.";
        List<Client> clients = clientDAO.findByAddress(address);
        assertEquals(1, clients.size());
        assertEquals(address, clients.get(0).getAddress());
    }

    @Test
    void findByPhoneNumber() {
        String phoneNumber = "89999999999";
        List<Client> clients = clientDAO.findByPhoneNumber(phoneNumber);
        assertEquals(1, clients.size());
        assertEquals(phoneNumber, clients.get(0).getPhone_number());
    }

    @Test
    void authenticate() {
        String email = "aaa2003@gmail.com";
        String password = "12345";
        boolean isAuthenticated = clientDAO.authenticate(email, password);
        assertTrue(isAuthenticated);
    }

    @Test
    void findClientsRentingFilm() {
        int filmCopyId = 1;
        List<Client> clients = clientDAO.findClientsRentingFilm(filmCopyId);
        assertEquals(1, clients.size());
    }

    @Test
    void findClientsWithOverdueRentals() {
        List<Client> clients = clientDAO.findClientsWithOverdueRentals();
        assertEquals(0, clients.size());
    }

    @Test
    void updateClientInfo() {
        Client client = clientDAO.getById(1);
        String newName = "Anna_2";
        client.setFull_name(newName);
        clientDAO.update(client);
        Client updatedClient = clientDAO.getById(1);
        assertEquals(newName, updatedClient.getFull_name());
    }

    @Test
    void countClients() {
        List<Client> allClients = (List<Client>) clientDAO.getAll();
        assertEquals(9, allClients.size());
    }

    @Test
    void countClients2() {
        assertEquals(clientDAO.countClients(), 9);
    }

    @Test
    void addClients() {
        List<Client> employees = new ArrayList<>();
        employees.add(new Client(11, "aaa2003@gmail.com",
                "12345", "anna", "moscow, krasnokaz st.", "89999999999"));
        employees.add(new Client(12, "bbb2003@gmail.com",
                "qwerty", "nikita", "moscow, len gory 1", "88888888888"));
        employees.add(new Client(13, "ccc2003@gmail.com",
                "zxcvbnmnn", "adelina", "moscow, ramenskaya 15", "877777777"));

        clientDAO.saveCollection(employees);

        Client client = clientDAO.getById(11);
        assertEquals(11, client.getId());
        assertEquals("anna", client.getFull_name());
        assertEquals("aaa2003@gmail.com", client.getEmail());
        assertEquals("89999999999", client.getPhone_number());
    }
}