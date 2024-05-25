package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;
import ru.msu.video_hosting.model.Client;

import java.util.List;

public interface HistoryDAO extends CommonDAO<History, Integer>{
    List<History> findByClient(Client client);
    List<History> findByFilmCopy(FilmCopies filmCopy);
}
