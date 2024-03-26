package ru.msu.video_hosting.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(schema ="video_host_schema", name = "clients")
@Getter
@Setter
@ToString
public class Client implements CommonEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    private Integer clientId;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Column(nullable = false, name = "user_password")
    @NonNull
    private String password;

    @Column(nullable = false, name = "full_name")
    @NonNull
    private String full_name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone_number;


    public Client() {}

    public Integer getId() {
        return this.clientId;
    }

    /**
     * Constructor with all columns
     */
    public Client(
            Integer client_id,
            String email,
            String user_password,
            String full_name,
            String address,
            String phone_number
    ) {
        this.clientId = client_id;
        this.email = email;
        this.password = user_password;
        this.full_name = full_name;
        this.address = address;
        this.phone_number = phone_number;
    }

    /**
     * Constructor without phone number and address
     */
    public Client(
            Integer client_id,
            String email,
            String user_password,
            String full_name
    ) {
        this.clientId = client_id;
        this.email = email;
        this.password = user_password;
        this.full_name = full_name;
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
        Client other = (Client) o;
        return Objects.equals(clientId, other.clientId)
                && email.equals(other.email)
                && password.equals(other.password)
                && full_name.equals(other.full_name);
    }

}