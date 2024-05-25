package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.msu.video_hosting.DAO.FilmCopyDAO;

import java.util.List;
import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "storage_info")
@Getter
@Setter
@ToString
public class StorageInfo implements CommonEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "storage_info_id")
    private Integer storageInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film filmId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "film_copies")
    private List<Integer> filmCopies;

    @Column(nullable = false, name = "storage_device_type")
    private String storageDeviceType;

    @Column(nullable = false, name = "full_amount")
    private Integer fullAmount;

    @Column(nullable = false, name = "free_amount")
    private Integer freeAmount;

    @Column(nullable = false, name = "price")
    private Double price;

    public StorageInfo() {}

    public Integer getId() {
        return this.storageInfoId;
    }

    /**
     * Constructor
     */
    public StorageInfo(
            Integer storage_info_id,
            Film film_id,
            List<Integer> film_copies,
            String storage_device_type,
            Integer full_amount,
            Integer free_amount,
            Double price
    ) {
        this.storageInfoId = storage_info_id;
        this.filmId = film_id;
        this.filmCopies = film_copies;
        this.storageDeviceType = storage_device_type;
        this.fullAmount= full_amount;
        this.freeAmount = free_amount;
        this.price = price;
    }

    public void removeCopies(EntityManager entityManager) {
        List<Integer> copyIds = this.filmCopies;
        for (Integer copyId : copyIds) {
            FilmCopies copy = entityManager.find(FilmCopies.class, copyId);
            entityManager.remove(copy);
    }
}


    /**
     * Check if objects are equals
     *
     * @param o - object to check
     * @return true if object are equals, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageInfo other = (StorageInfo) o;
        return Objects.equals(storageInfoId, other.storageInfoId)
                && Objects.equals(filmId, other.filmId)
                && Objects.equals(filmCopies, other.filmCopies)
                && Objects.equals(storageDeviceType, other.storageDeviceType)
                && Objects.equals(fullAmount, other.fullAmount)
                && Objects.equals(freeAmount, other.freeAmount)
                && Objects.equals(price, other.price);
    }
}
