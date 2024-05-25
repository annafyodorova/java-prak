package ru.msu.video_hosting.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.msu.video_hosting.DAO.impl.HistoryDAOImpl;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;

import java.util.List;
@Service
public class HistoryService extends CommonService<History, HistoryDAOImpl> {
    @Autowired
    public HistoryService(SessionFactory sessionFactory) {
        super(new HistoryDAOImpl(sessionFactory));
    }

    public List<History> findByClient(Client client) {
        return dao.findByClient(client);
    }

    public List<History> findByFilmCopy(FilmCopies filmCopy) {
        return dao.findByFilmCopy(filmCopy);
    }
}
