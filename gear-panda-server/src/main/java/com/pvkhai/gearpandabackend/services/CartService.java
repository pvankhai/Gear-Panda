package com.pvkhai.gearpandabackend.services;

import com.pvkhai.gearpandabackend.models.Cart;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import org.springframework.http.ResponseEntity;


public interface CartService {
    ResponseEntity<ResponseObject> getAllProductCart(String userId);

    ResponseEntity<ResponseObject> addNewProductCart(Cart cart);

    ResponseEntity<ResponseObject> deleteProductCart(String userId, String code, Long quantity);

    ResponseEntity<ResponseObject> showTotalMoney(String userId);

}
