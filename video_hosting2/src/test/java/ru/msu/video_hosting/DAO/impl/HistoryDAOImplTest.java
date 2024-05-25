package ru.msu.video_hosting.DAO.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.HistoryDAO;
import ru.msu.video_hosting.model.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class HistoryDAOImplTest {
    @Autowired
    private HistoryDAO historyDAO;

    @Autowired
    private EntityManager entityManager;

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
        assertEquals(3, history.getClientId());
        assertEquals(3, history.getFilmCopyId());
        assertEquals( LocalDate.of(2023,4,1), history.getDateOfIssue());
        assertEquals( LocalDate.of(2023,5,1), history.getDateOfReturn());
    }

    @Test
    void findByClient() {
        Client client = entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList().get(1);
        List<History> historyList = historyDAO.findByClient(client);
        assertFalse(historyList.isEmpty());
        for (History history : historyList) {
            assertEquals(client.getId(), history.getClientId());
        }
    }

    @Test
    void findByFilmCopy() {
        FilmCopies filmCopy = entityManager.createQuery("SELECT fc FROM FilmCopies fc", FilmCopies.class).getResultList().get(0);
        List<History> historyList = historyDAO.findByFilmCopy(filmCopy);
        assertFalse(historyList.isEmpty());
        for (History history : historyList) {
            assertEquals(filmCopy.getId(), history.getFilmCopyId());
        }
    }
}