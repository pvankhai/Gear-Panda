package com.pvkhai.gearpandabackend.services.impl;

import com.pvkhai.gearpandabackend.dto.UserRegistrationDTO;
import com.pvkhai.gearpandabackend.dto.UserUpdateDTO;
import com.pvkhai.gearpandabackend.exceptions.UserNotFoundException;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.models.Roles;
import com.pvkhai.gearpandabackend.models.Roles;
import com.pvkhai.gearpandabackend.models.User;
import com.pvkhai.gearpandabackend.repositories.UserRepository;
import com.pvkhai.gearpandabackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    // Regex Email: xxx@xx
    private Pattern patternEmail = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Regex Username: Includes letters (a-z), numbers (0-9) and periods (.)
    private Pattern patternUsername = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(1L);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoleToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Collection<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toList());
    }


    @Override
    public User save(UserRegistrationDTO userRegistrationDTO) {
        User user = new User(userRegistrationDTO.getUsername(), passwordEncoder.encode(userRegistrationDTO.getPassword()),
                userRegistrationDTO.getFirstName(), userRegistrationDTO.getLastName(),
                "NA", "NA", 0L, "NA",null , 1L, Arrays.asList(new Roles("ROLE_USER")));
        return userRepository.save(user);
    }


    /**
     * Get the list of users in the system with the role ADMIN
     *
     * @return
     */
    public ResponseEntity<ResponseObject> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get all users successfully!", userRepository.findAll()));
    }


    /**
     * Get user information based on id on the path
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> getUserById(Long id) {
        return userRepository.findById(id).isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Get all users successfully!", userRepository.findAll())) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("FAILED", "Not found user " + id, "N/A"));
    }


    /**
     * Register new users with role USER
     *
     * @param userRegistrationDTO
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> addNewUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRegistrationDTO.getFirstName() == null || userRegistrationDTO.getFirstName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("FAILED", "First name not null!", "N/A"));
        } else {
            if (userRegistrationDTO.getLastName() == null || userRegistrationDTO.getLastName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("FAILED", "Last name not null!", "N/A"));
            } else {
                if (userRegistrationDTO.getUsername() == null || userRegistrationDTO.getUsername().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject("FAILED", "Username not null!", "N/A"));
                } else {
                    if (userRegistrationDTO.getPassword() == null || userRegistrationDTO.getPassword().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                new ResponseObject("FAILED", "Password not null!", "N/A"));
                    } else {

                        Matcher matcherUsername = patternUsername.matcher(userRegistrationDTO.getUsername());
                        if (!matcherUsername.matches()) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                    new ResponseObject("FAILED", "Username can only use letters (a-z), numbers (0-9) and periods (.)", "N/A"));
                        }
                        User foundUsers = userRepository.findByUsername(userRegistrationDTO.getUsername().trim());
                        System.out.printf(String.valueOf(userRepository.findByUsername(userRegistrationDTO.getUsername())));
                        return (foundUsers != null) ?
                                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                                        new ResponseObject("FAILED", "User name already taken!", "N/A")) :
                                ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseObject("OK", "Insert User successfully!", save(userRegistrationDTO)));
                    }
                }
            }
        }
    }


    /**
     * Update user details with the role USER
     *
     * @param userUpdateDTO
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> updateUserDetail(UserUpdateDTO userUpdateDTO, Long id) {
        Matcher matcherEmail = patternEmail.matcher(userUpdateDTO.getEmail());
        if (!matcherEmail.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("FAILED", "Email invalidate", userRepository.findById(id))
            );
        }
        {
            userRepository.findById(id)
                    .map(user -> {
                        user.setFirstName(userUpdateDTO.getFirstName());
                        user.setLastName(userUpdateDTO.getLastName());
                        user.setGender(userUpdateDTO.getGender());
                        user.setEmail(userUpdateDTO.getEmail());
                        user.setAge(userUpdateDTO.getAge());
                        user.setAddress(userUpdateDTO.getAddress());
                        return userRepository.save(user);
                    }).orElseThrow(() -> new UserNotFoundException(id));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update user detail successfully!", userRepository.findById(id)));
    }


    /**
     * Update user details with the role ADMIN
     *
     * @param newUser
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> editUserInfo(User newUser, Long id) {
        Matcher matcherEmail = patternEmail.matcher(newUser.getEmail());
        if (!matcherEmail.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("FAILED", "Email invalidate", userRepository.findById(id))
            );
        }
        {
            userRepository.findById(id)
                    .map(user -> {
                        user.setPassword(newUser.getPassword());
                        user.setFirstName(newUser.getFirstName());
                        user.setLastName(newUser.getLastName());
                        user.setGender(newUser.getGender());
                        user.setEmail(newUser.getEmail());
                        user.setAge(newUser.getAge());
                        user.setAddress(newUser.getAddress());
                        user.setRoles(newUser.getRoles());
                        user.setEnabled(newUser.getEnabled());
                        return userRepository.save(user);
                    }).orElseThrow(() -> new UserNotFoundException(id));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update user detail successfully!", userRepository.findById(id)));
    }


    /**
     * Delete User by id with role ADMIN
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("FAILED", "User not found!", ""));
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Deleted user with " + id, ""));
    }




}

