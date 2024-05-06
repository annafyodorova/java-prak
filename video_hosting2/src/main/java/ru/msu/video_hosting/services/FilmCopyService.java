package ru.msu.video_hosting.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.msu.video_hosting.DAO.impl.FilmCopyDAOImpl;
import ru.msu.video_hosting.model.FilmCopies;
@Service
public class FilmCopyService extends CommonService<FilmCopies, FilmCopyDAOImpl> {
    @Autowired
    public FilmCopyService(SessionFactory sessionFactory) {
        super(new FilmCopyDAOImpl(sessionFactory));
    }
}
