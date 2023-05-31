package com.pvkhai.gearpandabackend.services;


import com.pvkhai.gearpandabackend.dto.UserRegistrationDTO;
import com.pvkhai.gearpandabackend.dto.UserUpdateDTO;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User save(UserRegistrationDTO userRegistrationDTO);

    ResponseEntity<ResponseObject> addNewUser(UserRegistrationDTO userRegistrationDTO);

    ResponseEntity<ResponseObject> getAllUsers();

    ResponseEntity<ResponseObject> getUserById(Long id);

    ResponseEntity<ResponseObject> updateUserDetail(UserUpdateDTO user, Long id);

    ResponseEntity<ResponseObject> editUserInfo(User user, Long id);

    ResponseEntity<ResponseObject> deleteUser(Long id);

}
