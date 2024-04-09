package ru.msu.video_hosting.model;

public enum CopyStatus {
    Выдан,
    Не_выдан;

    public static CopyStatus fromString(String strCopyStatus) {
        return switch (strCopyStatus.toLowerCase()) {
            case "выдан" -> CopyStatus.Выдан;
            case "не выдан" -> CopyStatus.Не_выдан;
            default -> throw new IllegalArgumentException("Недопустимое значение для статуса: " + strCopyStatus);
        };
    }

    public String toString() {
        return switch (this) {
            case Выдан-> "Выдан";
            case Не_выдан -> "Не выдан";
            default -> throw new IllegalArgumentException("Недопустимое значение для статуса: " + this);
        };
    }
}
