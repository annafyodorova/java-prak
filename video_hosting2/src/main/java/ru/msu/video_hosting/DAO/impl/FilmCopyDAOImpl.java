package ru.msu.video_hosting.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.FilmCopyDAO;
import ru.msu.video_hosting.model.FilmCopies;

@Repository
public class FilmCopyDAOImpl extends CommonDAOImpl<FilmCopies, Integer> implements FilmCopyDAO {
    @Autowired
    public FilmCopyDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        setEntityClass(FilmCopies.class);
    }
}