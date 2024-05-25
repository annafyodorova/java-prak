package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.model.FilmGenre;

import java.util.List;

public interface FilmDAO extends CommonDAO<Film, Integer> {

    Film findByTitle(String title);

    List<Film> findByGenre(String genre);

    List<Film> findByYearOfPremiere(int year);

    List<Film> findByCompany(String company);

    List<Film> findByDirector(String director);

    List<Film> findAllOrderByYearOfPremiere();
}
