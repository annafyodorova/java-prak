package ru.msu.video_hosting.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.msu.video_hosting.DAO.ClientDAO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class HistoryPK implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("clientId")
    private Client clientId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("filmCopyId")
    private FilmCopies filmCopyId;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;


    public HistoryPK() {}

    public HistoryPK(
            Client client,
            FilmCopies film_copy,
            LocalDate date_of_issue
    ) {
        this.clientId = client;
        this.filmCopyId = film_copy;
        this.dateOfIssue = date_of_issue;
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
        HistoryPK other = (HistoryPK) o;
        return Objects.equals(clientId, other.clientId)
                && Objects.equals(filmCopyId, other.filmCopyId)
                && Objects.equals(dateOfIssue, other.dateOfIssue);
    }
}
