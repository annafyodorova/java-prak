package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "film_copies")
@Getter
@Setter
@ToString
public class FilmCopies implements CommonEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "film_copies_id")
    private Integer filmCopiesId;

    //@Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private CopyStatus status;

    public FilmCopies() {}

    public Integer getId() {
        return this.filmCopiesId;
    }
    /**
     * Constructor
     */
    public FilmCopies(
            Integer FilmCopies_id,
            CopyStatus status
    ) {
        this.filmCopiesId = FilmCopies_id;
        this.status = status;
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
        FilmCopies other = (FilmCopies) o;
        return Objects.equals(filmCopiesId, other.filmCopiesId)
                && Objects.equals(status, other.status);
    }

}
