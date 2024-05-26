package ru.msu.video_hosting.model;

import lombok.Data;

import java.util.Map;

/**
 * Created by ytati
 * on 26.05.2024.
 */
@Data
public class OrderDTO {
    private double sum;
    private Map<Integer, Integer> storages;
}
