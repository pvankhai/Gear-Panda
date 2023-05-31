package com.pvkhai.gearpandabackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public UserRegistrationDTO(String firstName, String lastName, String username, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

}
