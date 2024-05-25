package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "history",
        uniqueConstraints= @UniqueConstraint(columnNames={"client_id", "film_copy_id", "date_of_issue"}))

@Getter
@Setter
@ToString
public class History implements CommonEntity<Integer>{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "film_copy_id")
    private Integer filmCopyId;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(nullable = false, name = "date_of_return")
    private LocalDate dateOfReturn;

    public History() {}


    /**
     * Constructor
     */
    public History(
            Integer id,
            Client client_id,
            FilmCopies film_copy_id,
            LocalDate date_of_issue,
            LocalDate date_of_return
    ) {
        this.id = id;
        this.clientId = client_id.getClientId();
        this.filmCopyId = film_copy_id.getFilmCopiesId();
        this.dateOfIssue = date_of_issue;
        this.dateOfReturn = date_of_return;
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
        History other = (History) o;
        return Objects.equals(id, other.id)
                &&Objects.equals(clientId, other.clientId)
                &&Objects.equals(filmCopyId, other.filmCopyId)
                &&Objects.equals(dateOfIssue, other.dateOfIssue)
                &&Objects.equals(dateOfReturn, other.dateOfReturn);
    }
}
