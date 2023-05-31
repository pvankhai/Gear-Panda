package com.pvkhai.gearpandabackend.controllers;

import com.pvkhai.gearpandabackend.models.Cart;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.repositories.CartRepository;
import com.pvkhai.gearpandabackend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    // Get all products to the cart
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getAllProductCart(@PathVariable String userId) {
        return cartService.getAllProductCart(userId);
    }

    // Add a new product to the cart
    @PostMapping()
    public ResponseEntity<ResponseObject> addNewProductCart(@RequestBody Cart newCart) {
        return cartService.addNewProductCart(newCart);
    }

    // Delete product to the cart
    @DeleteMapping()
    public ResponseEntity<ResponseObject> deleteProductCart(@RequestParam String userId, @RequestParam String code, @RequestParam Long quantity) {

        return cartService.deleteProductCart(userId, code, quantity);
    }

}
