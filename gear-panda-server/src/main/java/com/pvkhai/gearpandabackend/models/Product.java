package com.pvkhai.gearpandabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String type;
    private String name;
    private String brand;
    private String illustration;
    private String description;
    private Long price;
    private Long quantity;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "image_id", referencedColumnName = "id"))
    private Collection<Images> images;

    public Product() {

    }

    public Product(String code, String type, String name, String brand, String illustration, String description, Long price, Long quantity, Collection<Images> images) {
        super();
        this.code = code;
        this.type = type;
        this.name = name;
        this.brand = brand;
        this.illustration = illustration;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.images = images;
    }
}
