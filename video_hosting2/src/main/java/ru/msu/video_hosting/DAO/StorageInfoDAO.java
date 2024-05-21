package ru.msu.video_hosting.DAO;

import ru.msu.video_hosting.model.StorageInfo;

import java.util.List;
public interface StorageInfoDAO extends CommonDAO<StorageInfo, Integer>{
    List<StorageInfo> findByFilmId(String filmId);
    List<StorageInfo> findByDeviceType(String deviceType);
    List<StorageInfo> findAllByFilmTitle(String filmTitle);
    int getCountByFilmAndDevice(int filmId, String deviceType);
    int getTotalAvailableCopiesForFilm(int filmId);
    int getRemainingCopiesForFilm(int filmId);

}
