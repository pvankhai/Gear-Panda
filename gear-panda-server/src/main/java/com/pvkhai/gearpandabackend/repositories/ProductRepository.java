package com.pvkhai.gearpandabackend.repositories;

import com.pvkhai.gearpandabackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    List<Product> findByType(String type);

    List<Product> findByCode(String type);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.code,p.type,p.name,p.brand) LIKE %?1%") //HQl
    List<Product> findAll(String keyword);

    @Query("SELECT p.type, COUNT(p.type) AS quantity FROM Product p GROUP BY p.type")
    List<Optional> countNumberProduct();

}
