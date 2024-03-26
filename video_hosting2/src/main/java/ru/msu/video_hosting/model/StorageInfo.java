package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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

    @Column(nullable = false, name = "film_id")
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id", nullable = false)
    private Film filmId;

    @ElementCollection
    @Column(name = "film_copies")
    private List<Integer> filmCopies;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "storage_device_type")
    @NonNull
    private DeviceType storageDeviceType;

    @Column(nullable = false, name = "full_amount")
    @NonNull
    private Integer fullAmount;

    @Column(nullable = false, name = "free_amount")
    @NonNull
    private Integer freeAmount;

    @Column(nullable = false, name = "price")
    @NonNull
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
            DeviceType storage_device_type,
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
