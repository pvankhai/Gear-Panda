package com.pvkhai.gearpandabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roles;

    public Roles() {
    }

    public Roles(String roles) {
        super();
        this.roles = roles;
    }

}
