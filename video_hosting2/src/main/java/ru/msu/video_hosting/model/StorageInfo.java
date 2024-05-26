package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(schema ="video_host_schema", name = "storage_info")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StorageInfo implements CommonEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "storage_info_id")
    private Integer storageInfoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id", nullable = false)
    private Film filmId;

    @Column(name = "film_copies")
    private Integer[] filmCopies;

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
        this.storageDeviceType = storage_device_type;
        this.fullAmount= full_amount;
        this.freeAmount = free_amount;
        this.price = price;
    }

}

