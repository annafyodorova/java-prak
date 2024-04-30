package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.StorageInfoDAOImpl;
import ru.msu.video_hosting.model.StorageInfo;

import java.util.List;

public class StorageInfoService extends CommonService<StorageInfo, StorageInfoDAOImpl> {
    public StorageInfoService() {
        super(new StorageInfoDAOImpl());
    }

    public List<StorageInfo> findByFilmId(int filmId) {
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

}
