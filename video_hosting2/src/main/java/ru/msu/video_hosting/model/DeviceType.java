package ru.msu.video_hosting.model;

public enum DeviceType {
    Кассета,
    Диск;

    public static DeviceType fromString(String strDevType) {
        return switch (strDevType.toLowerCase()) {
            case "диск" -> DeviceType.Диск;
            case "кассета" -> DeviceType.Кассета;
            default -> throw new IllegalArgumentException("Недопустимое значение для типа устройства: " + strDevType);
        };
    }

    public String toString() {
        return switch (this) {
            case Кассета -> "Кассета";
            case Диск -> "Диск";
            default -> throw new IllegalArgumentException("Недопустимое значение для типа устройства: " + this);
        };
    }
}