package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.FilmDAOImpl;
import ru.msu.video_hosting.model.Film;

import java.util.List;

public class FilmService extends CommonService<Film, FilmDAOImpl> {
    public FilmService() {
        super(new FilmDAOImpl());
    }

    public Film findByTitle(String title) {
        return dao.findByTitle(title);
    }

    public List<Film> findByGenre(String genre) {
        return dao.findByGenre(genre);
    }

    public List<Film> findByYearOfPremiere(int year) {
        return dao.findByYearOfPremiere(year);
    }

    List<Film> findByCompany(String company) {
        return dao.findByCompany(company);
    }

    public List<Film> findByDirector(String director) {
        return dao.findByDirector(director);
    }

    public List<Film> findAllOrderByYearOfPremiere() {
        return dao.findAllOrderByYearOfPremiere();
    }

}


