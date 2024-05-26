package ru.msu.video_hosting.services;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.msu.video_hosting.DAO.FilmCopyDAO;
import ru.msu.video_hosting.DAO.HistoryDAO;
import ru.msu.video_hosting.DAO.impl.StorageInfoDAOImpl;
import ru.msu.video_hosting.model.CopyStatus;
import ru.msu.video_hosting.model.FilmCopies;
import ru.msu.video_hosting.model.History;
import ru.msu.video_hosting.model.StorageInfo;

import java.time.LocalDate;
import java.util.List;
@Service
public class StorageInfoService extends CommonService<StorageInfo, StorageInfoDAOImpl> {
    private FilmCopyDAO filmCopyDAO;
    private HistoryDAO historyDAO;
    public StorageInfoService(SessionFactory sessionFactory, FilmCopyDAO filmCopyDAO, HistoryDAO historyDAO) {
        super(new StorageInfoDAOImpl(sessionFactory));
        this.filmCopyDAO = filmCopyDAO;
        this.historyDAO = historyDAO;
    }

    public List<StorageInfo> findByFilmId(String filmId) {
        return dao.findByFilmId(filmId);
    }

    public List<StorageInfo> findByDeviceType(String deviceType) {
        return dao.findByDeviceType(deviceType);
    }

    public List<StorageInfo> findAllByFilmTitle(String filmTitle) {
        return dao.findAllByFilmTitle(filmTitle);
    }

    public int getCountByFilmAndDevice(int filmId, String deviceType) {
        return dao.getCountByFilmAndDevice(filmId, deviceType);
    }

    public int getTotalAvailableCopiesForFilm(int filmId) {
        return dao.getTotalAvailableCopiesForFilm(filmId);
    }

    public int getRemainingCopiesForFilm(int filmId) {
        return dao.getRemainingCopiesForFilm(filmId);
    }

    public StorageInfo findByCopyId(int copyId){
        return dao.findByCopyId(copyId);
    }

    public double reserveCopies(int id, int toReserve, int clientId) {
        StorageInfo si = dao.getById(id);

        if(si.getFreeAmount() < toReserve)
            throw new IllegalArgumentException("Not enough copies available");

        si.setFreeAmount(si.getFreeAmount() - toReserve);
        dao.save(si);

        List<Integer> copyIds = List.of(si.getFilmCopies());
        int reserved = 0;
        for (int i = 0; reserved < toReserve && i < copyIds.size(); i++) {
            FilmCopies copy = filmCopyDAO.getById(copyIds.get(i));
            if (copy.getStatus().equals(CopyStatus.Выдан)) {
                continue;
            }
            reserved++;
            History history = new History();
            history.setClientId(clientId);
            history.setFilmCopyId(copy.getId());
            history.setDateOfIssue(LocalDate.now());
            history.setDateOfReturn(LocalDate.now().plusMonths(1));
            historyDAO.save(history);
            copy.setStatus(CopyStatus.Выдан);
            filmCopyDAO.save(copy);
        }
        return si.getPrice() * toReserve;
    }

}
