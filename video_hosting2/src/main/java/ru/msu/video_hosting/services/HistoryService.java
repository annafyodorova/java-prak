package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.HistoryDAOImpl;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;

import java.util.List;

public class HistoryService extends CommonService<History, HistoryDAOImpl> {
    public HistoryService() {
        super(new HistoryDAOImpl());
    }

    public List<History> findByClient(Client client) {
        return dao.findByClient(client);
    }

    public List<History> findByFilmCopy(FilmCopies filmCopy) {
        return dao.findByFilmCopy(filmCopy);
    }
}
