package ru.msu.video_hosting.DAO.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.video_hosting.DAO.StorageInfoDAO;
import ru.msu.video_hosting.model.DeviceType;
import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.model.FilmGenre;
import ru.msu.video_hosting.model.StorageInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class StorageInfoDAOImplTest {
    @Autowired
    private StorageInfoDAO storageInfoDAO;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        List<StorageInfo> storageInfos = new ArrayList<>();
        List<Integer> fimCopiesId = new ArrayList<>();
        for (int i=1; i<= 10; ++i) {
            fimCopiesId.add(i);
        }
        Film film1 = new Film(1, "Ла-Ла Ленд", "США", "Дэмьен Шазелл", 2016, FilmGenre.Мюзикл, "Миа и Себастьян выбирают между личным счастьем и амбициями. Трагикомичный мюзикл о компромиссе в жизни артиста");
        storageInfos.add(new StorageInfo(1, film1, fimCopiesId, DeviceType.Кассета, 10, 10, 1000.0));
        fimCopiesId.clear();
        for (int i=11; i<= 20; ++i) {
            fimCopiesId.add(i);
        }
        storageInfos.add(new StorageInfo(2, film1, fimCopiesId, DeviceType.Диск, 10, 10, 1000.0));

        fimCopiesId.clear();
        fimCopiesId.add(21);
        fimCopiesId.add(22);
        Film film2 = new Film(2, "Волк c Уолл-стрит", "США", "Мартин Скорсезе", 2013, FilmGenre.Драма);
        storageInfos.add(new StorageInfo(3, film2, fimCopiesId, DeviceType.Кассета, 2, 2, 1000.0));
        fimCopiesId.clear();
        fimCopiesId.add(23);
        storageInfos.add(new StorageInfo(4, film2, fimCopiesId, DeviceType.Диск, 1, 1, 1000.0));

        fimCopiesId.clear();
        for (int i=24; i<= 33; ++i) {
            fimCopiesId.add(i);
        }
        Film film3 = new Film(3, "Паразиты", "Корея Южная", "Пон Джун-хо", 2018, FilmGenre.Драма);
        storageInfos.add(new StorageInfo(5, film3, fimCopiesId, DeviceType.Кассета, 10, 10, 2000.0));
        fimCopiesId.clear();
        for (int i=34; i<= 38; ++i) {
            fimCopiesId.add(i);
        }
        storageInfos.add(new StorageInfo(6, film3, fimCopiesId, DeviceType.Диск, 5, 5, 1900.0));

        storageInfoDAO.saveCollection(storageInfos);
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM StorageInfo ").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void testUpdateStorageInfo() {
        StorageInfo storageInfo = storageInfoDAO.getById(1);
        storageInfo.setFullAmount(15);
        storageInfoDAO.update(storageInfo);
        StorageInfo updatedStorageInfo = storageInfoDAO.getById(1);
        assertEquals(15, updatedStorageInfo.getFullAmount());
    }

    @Test
    void testDeleteStorageInfo() {
        StorageInfo storageInfo = storageInfoDAO.getById(1);
        storageInfoDAO.delete(storageInfo);
        assertNull(storageInfoDAO.getById(1));

    }

    @Test
    void findByFilmId() {
        int filmId = 1;
        List<StorageInfo> storageInfos = storageInfoDAO.findByFilmId(filmId);
        assertEquals(2, storageInfos.size());
        for (StorageInfo storageInfo : storageInfos) {
            assertEquals(filmId, storageInfo.getFilmId().getId());
        }
    }

    @Test
    void findByDeviceType() {
        DeviceType deviceType = DeviceType.Кассета;
        List<StorageInfo> storageInfos = storageInfoDAO.findByDeviceType(deviceType.toString());
        assertEquals(4, storageInfos.size()); // Предполагаем, что у нас есть 4 записи с указанным типом устройства хранения
        for (StorageInfo storageInfo : storageInfos) {
            assertEquals(deviceType, storageInfo.getStorageDeviceType());
        }

    }

    @Test
    void findAllByFilmTitle() {
        String filmTitle = "Паразиты";
        List<StorageInfo> storageInfos = storageInfoDAO.findAllByFilmTitle(filmTitle);
        assertEquals(2, storageInfos.size());
        for (StorageInfo storageInfo : storageInfos) {
            assertEquals(filmTitle, storageInfo.getFilmId().getFilmTitle());
        }
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