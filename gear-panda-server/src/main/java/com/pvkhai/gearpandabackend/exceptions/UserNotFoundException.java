package com.pvkhai.gearpandabackend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not found the user of id " + id);
    }

}
