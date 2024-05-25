package ru.msu.video_hosting.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.msu.video_hosting.DAO.impl.FilmDAOImpl;
import ru.msu.video_hosting.model.Film;

import java.util.List;
@Service
public class FilmService extends CommonService<Film, FilmDAOImpl> {
    @Autowired
    public FilmService(SessionFactory sessionFactory) {
        super(new FilmDAOImpl(sessionFactory));
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


