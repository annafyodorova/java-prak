package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "history")
@Getter
@Setter
@ToString
public class History implements CommonEntity<HistoryPK>{
    @EmbeddedId
    private HistoryPK historyId;

    @Column(nullable = false, name = "date_of_return")
    @NonNull
    private LocalDate dateOfReturn;

    public History() {}

    public HistoryPK getId() {
        return this.historyId;
    }


    /**
     * Constructor
     */
    public History(
            Client client_id,
            FilmCopies film_copy_id,
            LocalDate date_of_issue,
            LocalDate date_of_return
    ) {
        this.historyId = new HistoryPK(client_id, film_copy_id, date_of_issue);
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
        return Objects.equals(historyId, other.historyId)
                &&Objects.equals(dateOfReturn, other.dateOfReturn);
    }
}
