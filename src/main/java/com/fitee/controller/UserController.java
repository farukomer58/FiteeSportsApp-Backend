package com.fitee.controller;


import com.fitee.dto.RegistrationResponse;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import com.fitee.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.net.URI;
import java.util.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
//    private final JwtProvider jwtProvider;


    /**
     * GET: Get all users
     */
    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * GET: Get SINGLE user by ID
     */
    @GetMapping("{id}")
    public User getUserById(@PathVariable long id) {
        return userRepository.findById(id).get();
    }

    /**
     * POST: Registers a new user in the database and sends a verification email.
     *
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) throws MessagingException {
        User newUser = userService.registerUser(user);
        newUser.setLocked(1);
//        String jwtToken = jwtProvider.createVerifyingToken(newUser.getUsername());
//        userService.sendVerifyingEmail(user, jwtToken);

        // Current Request URI creation
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/users/{id}").buildAndExpand(newUser.getId()).toUri();
        // Custom header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location.toString());
        // Response code CREATED with Custom body
        return new ResponseEntity<>(new RegistrationResponse(newUser.getEmail(), "User successfully registered"), headers, HttpStatus.CREATED);
    }

    /**
     * POST: Login user, check credentials
     */
    @GetMapping("/login")
    public String loginUser() {
        return "";
    }

//    /**
//     * Retrieves current context user-info.
//     */
//    @Secured({RoleType.SUPPLIER, RoleType.CUSTOMER})
//    @GetMapping("/me")
//    public UserResponse getUserInfo() {
//        UserEntity user = userService.getCurrentUser();
//        return new UserResponse(user);
//    }
//
//    /**
//     * Verifies an user by token.
//     */
//    @GetMapping("/verify")
//    public ResponseEntity verifyUser(@RequestParam String token) {
//        userService.verifyUser(token);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    /**
//     * Retrieves account locked status by username.
//     */
//    @GetMapping("/info/{username}")
//    public boolean getUserFromUsername(@PathVariable String username) {
//        return this.userRepository.findByUsername(username).get().isLocked();
//    }

}
