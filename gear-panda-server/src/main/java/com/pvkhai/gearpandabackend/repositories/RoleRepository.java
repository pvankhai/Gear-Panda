package com.pvkhai.gearpandabackend.repositories;

import com.pvkhai.gearpandabackend.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {

}
