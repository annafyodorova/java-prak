package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.FilmCopyDAOImpl;
import ru.msu.video_hosting.model.FilmCopies;

public class FilmCopyService extends CommonService<FilmCopies, FilmCopyDAOImpl> {
    public FilmCopyService() {
        super(new FilmCopyDAOImpl());
    }
}
