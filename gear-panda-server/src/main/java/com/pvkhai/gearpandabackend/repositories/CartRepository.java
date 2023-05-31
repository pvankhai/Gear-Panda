package com.pvkhai.gearpandabackend.repositories;

import com.pvkhai.gearpandabackend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT DISTINCT c.code FROM Cart c WHERE c.userId = ?1")
    List<String> findAllCode(String userId);

    @Query("SELECT c FROM Cart c WHERE c.userId = ?1 AND c.code = ?2")
    Cart getProductsInCart(String userId, String code);

}
