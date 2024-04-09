package ru.msu.video_hosting.model;

public enum FilmGenre {
    Боевик,
    Драма,
    Комедия,
    Ужасы,
    Фантастика,
    Триллер,
    Мюзикл,
    Другой;

    public static FilmGenre fromString(String strGenre) {
        return switch (strGenre.toLowerCase()) {
            case "боевик" -> FilmGenre.Боевик;
            case "драма" -> FilmGenre.Драма;
            case "комедия" -> FilmGenre.Комедия;
            case "ужасы" -> FilmGenre.Ужасы;
            case "фантастика" -> FilmGenre.Фантастика;
            case "триллер" -> FilmGenre.Триллер;
            case "мюзикл" -> FilmGenre.Мюзикл;
            case "другой" -> FilmGenre.Другой;
            default -> throw new IllegalArgumentException("Недопустимое значение для жанра фильма: " + strGenre);
        };
    }

    public String toString() {
        return switch (this) {
            case Боевик -> "Боевик";
            case Драма -> "Драма";
            case Комедия -> "Комедия";
            case Ужасы -> "Ужасы";
            case Фантастика -> "Фантастика";
            case Триллер -> "Триллер";
            case Мюзикл -> "Мюзикл";
            case Другой -> "Другой";
            default -> throw new IllegalArgumentException("Недопустимое значение для жанра фильма: " + this);
        };
    }
}