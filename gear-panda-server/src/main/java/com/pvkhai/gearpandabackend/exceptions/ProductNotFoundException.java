package com.pvkhai.gearpandabackend.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Could not found product with Id = " + id);
    }

}
