package ru.msu.video_hosting.DAO.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.FilmDAO;
import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.model.FilmGenre;

import java.util.ArrayList;
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
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Ла-Ла Ленд", "США", "Дэмьен Шазелл", 2016, FilmGenre.Мюзикл, "Миа и Себастьян выбирают между личным счастьем и амбициями. Трагикомичный мюзикл о компромиссе в жизни артиста"));
        films.add(new Film(2, "Волк c Уолл-стрит", "США", "Мартин Скорсезе", 2013, FilmGenre.Драма));
        films.add(new Film(3, "Паразиты", "Корея Южная", "Пон Джун-хо", 2018, FilmGenre.Драма));

        filmDAO.saveCollection(films);
    }

    @AfterEach
    void clean() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Film").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void getFields() {
        Film film = filmDAO.getById(1);
        assertEquals(1, film.getId());
        assertEquals("Ла-Ла Ленд", film.getFilmTitle());
        assertEquals(2016, film.getYearOfPremiere());
        assertEquals("США", film.getFilmCompany());
        assertEquals("Дэмьен Шазелл", film.getDirector());
    }

    @Test
    void update() {
        String newName = "бла-бла-лэнд>";
        String newDescription = "";
        int newYear = 00;
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
    void delete() {
        Film deleteFilm = filmDAO.getById(1);
        filmDAO.delete(deleteFilm);
        Film deletedFilm = filmDAO.getById(1);
        assertNull(deletedFilm);

        filmDAO.deleteById(2);
        Film deletedFilm2 = filmDAO.getById(2);
        assertNull(deletedFilm2);

        Collection<Film> FilmListAll = filmDAO.getAll();
        assertEquals(1, FilmListAll.size());
    }

    @Test
    void findByTitle() {
        String title = "Ла-Ла Ленд";
        Film film = filmDAO.findByTitle(title);
        assertEquals(title, film.getFilmTitle());
    }

    @Test
    void findByGenre() {
        FilmGenre genre = FilmGenre.Драма;
        List<Film> films = filmDAO.findByGenre(genre);
        assertEquals(2, films.size());
        for (Film film : films) {
            assertEquals(genre, film.getFilmGenre());
        }
    }

    @Test
    void findByYearOfPremiere() {
        int year = 2018;
        List<Film> films = filmDAO.findByYearOfPremiere(year);
        assertEquals(1, films.size());
        assertEquals(year, films.get(0).getYearOfPremiere());
    }

    @Test
    void findByCompany() {
        String company = "США";
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
        assertEquals(3, films.size());
        assertEquals(2013, films.get(0).getYearOfPremiere());
        assertEquals(2016, films.get(1).getYearOfPremiere());
        assertEquals(2018, films.get(2).getYearOfPremiere());
    }
}