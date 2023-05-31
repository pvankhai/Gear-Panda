package com.pvkhai.gearpandabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Table(name = "image")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    public Images() {
    }

    public Images( String name, String type, byte[] data) {
       super();
        this.name = name;
        this.type = type;
        this.data = data;
    }

}
