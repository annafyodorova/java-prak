package ru.msu.video_hosting.DAO.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.DAO.FilmCopyDAO;
import ru.msu.video_hosting.DAO.HistoryDAO;
import ru.msu.video_hosting.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class HistoryDAOImplTest {
    @Autowired
    private HistoryDAO historyDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private FilmCopyDAO filmCopyDAO;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        List<Client> employees = new ArrayList<>();
        employees.add(new Client(1, "aaa2003@gmail.com",
                "12345", "anna", "moscow, krasnokaz st.", "89999999999"));
        employees.add(new Client(2, "bbb2003@gmail.com",
                "qwerty", "nikita", "moscow, len gory 1", "88888888888"));
        employees.add(new Client(3, "ccc2003@gmail.com",
                "zxcvbnmnn", "adelina", "moscow, ramenskaya 15", "877777777"));

        clientDAO.saveCollection(employees);

        List<FilmCopies> filmCopies = new ArrayList<>();
        filmCopies.add(new FilmCopies(1, CopyStatus.Выдан));
        filmCopies.add(new FilmCopies(2, CopyStatus.Выдан));
        filmCopies.add(new FilmCopies(3, CopyStatus.Выдан));
        filmCopies.add(new FilmCopies(4, CopyStatus.Выдан));
        filmCopyDAO.saveCollection(filmCopies);

        List<History> histories = new ArrayList<>();
        histories.add(new History(clientDAO.getById(1),filmCopyDAO.getById(1), LocalDate.of(2024,4,1), LocalDate.of(2024,4,10)));
        histories.add(new History(clientDAO.getById(2),filmCopyDAO.getById(2), LocalDate.of(2024,4,2), LocalDate.of(2024,4,20)));
        histories.add(new History(clientDAO.getById(3),filmCopyDAO.getById(3), LocalDate.of(2024,5,1), LocalDate.of(2024,8,10)));
        histories.add(new History(clientDAO.getById(1),filmCopyDAO.getById(4), LocalDate.of(2024,7,1), LocalDate.of(2024,7,10)));
        historyDAO.saveCollection(histories);
    }

    @AfterEach
    void tearDown() {
        entityManager.createQuery("DELETE FROM History ").executeUpdate();
        entityManager.createQuery("DELETE FROM Client ").executeUpdate();
        entityManager.createQuery("DELETE FROM FilmCopies ").executeUpdate();
    }

    @Test
    void update() {
        List<History> histories = (List<History>) historyDAO.getAll();
        History history = histories.get(0);
        LocalDate newReturnDate = LocalDate.of(2024, 4, 15);
        history.setDateOfReturn(newReturnDate);
        historyDAO.update(history);
        History updatedHistory = historyDAO.getById(history.getId());
        assertEquals(newReturnDate, updatedHistory.getDateOfReturn());
    }

    @Test
    void delete() {
        List<History> histories = (List<History>) historyDAO.getAll();
        History history = histories.get(0);
        historyDAO.delete(history);
        assertNull(historyDAO.getById(history.getId()));
        assertEquals(3, historyDAO.getAll().size());
    }

    @Test
    void getFields() {
        List<History> histories = (List<History>) historyDAO.getAll();
        History history = histories.get(0);
        assertEquals(1, history.getHistoryId().getClientId().getId());
        assertEquals(1, history.getHistoryId().getFilmCopyId().getFilmCopiesId());
        assertEquals( LocalDate.of(2024,4,1), history.getHistoryId().getDateOfIssue());
        assertEquals( LocalDate.of(2024,4,10), history.getDateOfReturn());
    }

    @Test
    void findByClient() {
        Client client = entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList().get(0); // Получаем первого клиента
        List<History> historyList = historyDAO.findByClient(client);
        assertFalse(historyList.isEmpty());
        for (History history : historyList) {
            assertEquals(client.getId(), history.getHistoryId().getClientId().getId());
        }
    }

    @Test
    void findByFilmCopy() {
        FilmCopies filmCopy = entityManager.createQuery("SELECT fc FROM FilmCopies fc", FilmCopies.class)
                .getResultList().get(0); // Получаем первую копию фильма
        List<History> historyList = historyDAO.findByFilmCopy(filmCopy);
        assertFalse(historyList.isEmpty());
        for (History history : historyList) {
            assertEquals(filmCopy.getId(), history.getHistoryId().getFilmCopyId().getFilmCopiesId());
        }
    }
}