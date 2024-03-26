package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "films")
@Getter
@Setter
@ToString
public class Film implements CommonEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "film_id")
    private Integer filmId;

    @Column(nullable = false, name = "film_title")
    @NonNull
    private String filmTitle;

    @Column(nullable = false, name = "film_company")
    @NonNull
    private String filmCompany;

    @Column(nullable = false, name = "director")
    @NonNull
    private String director;

    @Column(nullable = false, name = "year_of_premiere")
    @NonNull
    private int yearOfPremiere;

    @Column(nullable = false, name = "film_genre")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NonNull
    @Enumerated(EnumType.STRING)
    private FilmGenre filmGenre;

    @Column(nullable = true, name = "description")
    private String description;

    public Film() {}

    public Integer getId() {
        return this.filmId;
    }

    /**
     * Constructor with all columns
     */
    public Film(
            Integer film_id,
            String film_title,
            String film_company,
            String director,
            int year_of_premiere,
            FilmGenre film_genre,
            String description
    ) {
        this.filmId = film_id;
        this.filmTitle = film_title;
        this.filmCompany = film_company;
        this.director = director;
        this.yearOfPremiere = year_of_premiere;
        this.filmGenre = film_genre;
        this.description = description;
    }

    /**
     * Constructor without description
     */
    public Film(
            Integer film_id,
            String film_title,
            String film_company,
            String director,
            int year_of_premiere,
            FilmGenre film_genre
    ) {
        this.filmId = film_id;
        this.filmTitle = film_title;
        this.filmCompany = film_company;
        this.director = director;
        this.yearOfPremiere = year_of_premiere;
        this.filmGenre = film_genre;
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
        Film other = (Film) o;
        return Objects.equals(filmId, other.filmId)
                && filmTitle.equals(other.filmTitle)
                && filmCompany.equals(other.filmCompany)
                && director.equals(other.director)
                && Objects.equals(yearOfPremiere, other.yearOfPremiere)
                && Objects.equals(filmGenre, other.filmGenre);
    }


}
