package com.pvkhai.gearpandabackend.repositories;

import com.pvkhai.gearpandabackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


}
