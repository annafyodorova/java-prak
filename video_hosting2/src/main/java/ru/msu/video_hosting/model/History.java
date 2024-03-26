package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "history")
@Getter
@Setter
@ToString
@IdClass(HistoryPK.class)
public class History implements CommonEntity<HistoryPK>{
    @Id
    @Column(nullable = false, name = "client_id")
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client clientId;

    @Id
    @Column(nullable = false, name = "film_copy_id")
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_copies_id")
    private FilmCopies filmCopyId;

    @Id
    @Column(nullable = false, name = "date_of_issue")
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date dateOfIssue;

    @Column(nullable = false, name = "date_of_return")
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date dateOfReturn;

    public History() {}

    public HistoryPK getId() {
        return new HistoryPK(clientId.getClientId(), filmCopyId.getFilmCopiesId(), dateOfIssue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Constructor
     */
    public History(
            Client client_id,
            FilmCopies film_copy_id,
            Date date_of_issue,
            Date date_of_return
    ) {
        this.clientId = client_id;
        this.filmCopyId = film_copy_id;
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
        return Objects.equals(clientId, other.clientId)
                && Objects.equals(filmCopyId, other.filmCopyId)
                && Objects.equals(dateOfIssue, other.dateOfIssue);
    }
}
