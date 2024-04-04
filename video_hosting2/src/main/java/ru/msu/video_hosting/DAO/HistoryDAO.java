package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.HistoryPK;

import java.util.List;

public interface HistoryDAO extends CommonDAO<History, HistoryPK>{
    List<History> findByClient(Client client);
    List<History> findByFilmCopy(FilmCopies filmCopy);
}
