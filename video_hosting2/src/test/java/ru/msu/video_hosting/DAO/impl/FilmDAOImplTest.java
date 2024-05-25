package ru.msu.video_hosting.DAO.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.msu.video_hosting.DAO.FilmDAO;
import ru.msu.video_hosting.DAO.StorageInfoDAO;
import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.model.FilmGenre;
import ru.msu.video_hosting.model.StorageInfo;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class FilmDAOImplTest {
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private StorageInfoDAO storageInfoDAO;

    @Test
    void getFields() {
        Film film = filmDAO.getById(1);
        assertEquals(1, film.getId());
        assertEquals("Ла-Ла Ленд", film.getFilmTitle());
        assertEquals(2016, film.getYearOfPremiere());
        assertEquals("Дэмьен Шазелл", film.getDirector());
    }

    @Test
    void update() {
        String newName = "бла-бла-лэнд>";
        String newDescription = "";
        int newYear = 3000;
        String newCompany = "Самали";
        String newDirector = "Режиссеер";

        Film updateFilm = filmDAO.getById(1);
        updateFilm.setFilmTitle(newName);
        updateFilm.setDescription(newDescription);
        updateFilm.setYearOfPremiere(newYear);
        updateFilm.setFilmCompany(newCompany);
        updateFilm.setDirector(newDirector);
        filmDAO.update(updateFilm);

        Film updatedFilm = filmDAO.getById(1);
        assertEquals(newName, updatedFilm.getFilmTitle());
        assertEquals(newDescription, updatedFilm.getDescription());
        assertEquals(newYear, updatedFilm.getYearOfPremiere());
        assertEquals(newCompany, updatedFilm.getFilmCompany());
        assertEquals(newDirector, updatedFilm.getDirector());
    }

    @Test
    @Transactional
    void delete() {
        Film deleteFilm = filmDAO.getById(20);
        assertNotNull(deleteFilm);
        filmDAO.delete(deleteFilm);

        Film deletedFilm = filmDAO.getById(20);
        assertNull(deletedFilm);

        Collection<Film> filmListAll = filmDAO.getAll();
        assertEquals(19, filmListAll.size());

        List<StorageInfo> storageInfos = storageInfoDAO.findByFilmId(20);
        assertTrue(storageInfos.isEmpty());

    }

    @Test
    void findByTitle() {
        String title = "Паразиты";
        Film film = filmDAO.findByTitle(title);
        assertEquals(title, film.getFilmTitle());
    }

    @Test
    void findByGenre() {
        List<Film> films = filmDAO.findByGenre(FilmGenre.Драма.toString());
        assertEquals(7, films.size());
        for (Film film : films) {
            assertEquals(FilmGenre.Драма.toString(), film.getFilmGenre());
        }
    }

    @Test
    void findByYearOfPremiere() {
        int year = 2008;
        List<Film> films = filmDAO.findByYearOfPremiere(year);
        assertEquals(1, films.size());
        assertEquals(year, films.get(0).getYearOfPremiere());
    }

    @Test
    void findByCompany() {
        String company = "СССР";
        List<Film> films = filmDAO.findByCompany(company);
        assertEquals(2, films.size());
        for (Film film : films) {
            assertEquals(company, film.getFilmCompany());
        }
    }

    @Test
    void findByDirector() {
        String director = "Мартин Скорсезе";
        List<Film> films = filmDAO.findByDirector(director);
        for (Film film : films) {
            assertEquals(director, film.getDirector());
        }
    }

    @Test
    void findAllOrderByYearOfPremiere() {
        List<Film> films = filmDAO.findAllOrderByYearOfPremiere();
        assertEquals(19, films.size());
        assertEquals(1965, films.get(0).getYearOfPremiere());
    }
}