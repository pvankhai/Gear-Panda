package com.pvkhai.gearpandabackend.services.impl;

import com.pvkhai.gearpandabackend.models.Cart;
import com.pvkhai.gearpandabackend.models.Product;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.repositories.CartRepository;
import com.pvkhai.gearpandabackend.repositories.ProductRepository;
import com.pvkhai.gearpandabackend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;


    /**
     * Get all products from user's cart
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> getAllProductCart(String userId) {
        if (cartRepository.findAllCode(userId).size() != 0) {
            for (int i = 0; i < cartRepository.findAllCode(userId).size(); i++) {
                List<Product> products = productRepository.findByCode(cartRepository.findAllCode(userId).get(i));
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Get all products to the cart successfully", products));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get all products to the cart successfully!", "N/A"));
    }


    /**
     * Add product to user's cart
     *
     * @param newCart
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> addNewProductCart(Cart newCart) {
        Cart cart1 = cartRepository.getProductsInCart(newCart.getUserId(), newCart.getCode());
        if (cart1 == null) {
            cartRepository.save(newCart);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "The product was added to the cart successfully!", newCart));
        } else {
            cart1.setQuantity(cart1.getQuantity() + newCart.getQuantity());
            cartRepository.save(cart1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "The product was added to the cart successfully!", cart1));
        }
    }


    /**
     * Delete product from user's cart
     *
     * @param userId
     * @param code
     * @param quantity
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> deleteProductCart(String userId, String code, Long quantity) {
        Cart cart1 = cartRepository.getProductsInCart(userId, code);
        if (cart1 != null) {
            if (cart1.getQuantity() > 1) {
                if ((cart1.getQuantity() - quantity) <= 0) {
                    cartRepository.deleteById(cart1.getId());
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("OK", "Delete product to the cart successfully!", "N/A"));
                }
                cart1.setQuantity(cart1.getQuantity() - quantity);
                cartRepository.save(cart1);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Delete product to the cart successfully!", "N/A"));
            }
            cartRepository.deleteById(cart1.getId());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete product to the cart successfully!", "N/A"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("FAILED", "Cannot found product!", "N/A"));

    }


    /**
     * Get the total money of products from the user's cart
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> showTotalMoney(String userId) {
        Long totalMoney = 0L;
        List<Product> products = null;
        if (cartRepository.findAllCode(userId).size() != 0) {
            for (int i = 0; i < cartRepository.findAllCode(userId).size(); i++) {
                products = productRepository.findByCode(cartRepository.findAllCode(userId).get(i));
            }
            for (int j = 0; j < products.size(); j++) {
                totalMoney = totalMoney + products.get(j).getPrice() * cartRepository.getProductsInCart(userId, products.get(j).getCode()).getQuantity();
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Get total money successfully!", totalMoney));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("FAILED", "Cannot found product!", "N/A"));
    }

}
