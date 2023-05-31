package com.pvkhai.gearpandabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private Long age;
    private String address;
    @Lob
    private byte[] avatar;
    private Long enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<Roles> roles;


    public User() {

    }

    public User(String username, String password, String firstName, String lastName, String gender, String email, Long age, String address, byte[] avatar, Long enabled, Collection<Roles> roles) {
        super();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.address = address;
        this.avatar = avatar;
        this.enabled = enabled;
        this.roles = roles;
    }
}
