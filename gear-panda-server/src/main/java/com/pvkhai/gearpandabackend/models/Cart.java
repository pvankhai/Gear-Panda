package com.pvkhai.gearpandabackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String code;
    private Long quantity;

    public Cart() {

    }

    public Cart(String userId, String code, Long quantity) {
        super();
        this.userId = userId;
        this.code = code;
        this.quantity = quantity;
    }

}
