package ru.msu.video_hosting.model;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class HistoryPK implements Serializable {
    private Integer client;
    private Integer film_copy;
    private LocalDate date_of_issue;

    public HistoryPK() {}

    public HistoryPK(
            Integer client,
            Integer film_copy,
            LocalDate date_of_issue
    ) {
        this.client = client;
        this.film_copy = film_copy;
        this.date_of_issue = date_of_issue;
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
        return Objects.equals(client, other.client)
                && Objects.equals(film_copy, other.film_copy)
                && Objects.equals(date_of_issue, other.date_of_issue);
    }
}
