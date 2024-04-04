package ru.msu.video_hosting.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.video_hosting.DAO.FilmCopyDAO;
import ru.msu.video_hosting.model.FilmCopies;

@Repository
public class FilmCopyDAOImpl extends CommonDAOImpl<FilmCopies, Integer> implements FilmCopyDAO {
    public FilmCopyDAOImpl() {
        super(FilmCopies.class);
    }
}
