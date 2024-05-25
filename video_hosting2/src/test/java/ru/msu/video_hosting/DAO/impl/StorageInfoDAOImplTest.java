package ru.msu.video_hosting.DAO.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.StorageInfoDAO;
import ru.msu.video_hosting.model.DeviceType;
import ru.msu.video_hosting.model.StorageInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class StorageInfoDAOImplTest {
    @Autowired
    private StorageInfoDAO storageInfoDAO;

    @Test
    void testUpdateStorageInfo() {
        StorageInfo storageInfo = storageInfoDAO.getById(10);
        storageInfo.setFullAmount(15);
        storageInfoDAO.update(storageInfo);
        StorageInfo updatedStorageInfo = storageInfoDAO.getById(10);
        assertEquals(15, updatedStorageInfo.getFullAmount());
    }

    @Test
    void testDeleteStorageInfo() {
        StorageInfo storageInfo = storageInfoDAO.getById(19);
        storageInfoDAO.delete(storageInfo);
        assertNull(storageInfoDAO.getById(19));
    }

    @Test
    void findByFilmId() {
        int filmId = 1;
        List<StorageInfo> storageInfos = storageInfoDAO.findByFilmId(filmId);
        assertEquals(2, storageInfos.size());
    }

    @Test
    void findByDeviceType() {
        String deviceType = DeviceType.Кассета.toString();
        List<StorageInfo> storageInfos = storageInfoDAO.findByDeviceType(deviceType);
        assertEquals(18, storageInfos.size());
        for (StorageInfo storageInfo : storageInfos) {
            assertEquals(deviceType, storageInfo.getStorageDeviceType());
        }

    }

    @Test
    void findAllByFilmTitle() {
        String filmTitle = "Паразиты";
        List<StorageInfo> storageInfos = storageInfoDAO.findAllByFilmTitle(filmTitle);
        assertEquals(2, storageInfos.size());
    }

    @Test
    void getCountByFilmAndDevice() {
        int filmId = 1;
        DeviceType deviceType = DeviceType.Кассета;
        int count = storageInfoDAO.getCountByFilmAndDevice(filmId, deviceType.toString());
        assertEquals(1, count);
    }

    @Test
    void getTotalAvailableCopiesForFilm() {
        int filmId = 1;
        int totalAvailableCopies = storageInfoDAO.getTotalAvailableCopiesForFilm(filmId);
        assertEquals(20, totalAvailableCopies);
    }

    @Test
    void getRemainingCopiesForFilm() {
        int filmId = 1;
        int remainingCopies = storageInfoDAO.getRemainingCopiesForFilm(filmId);
        assertEquals(20, remainingCopies);
    }
}