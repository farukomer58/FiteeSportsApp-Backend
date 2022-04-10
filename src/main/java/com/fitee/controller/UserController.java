package com.fitee.controller;


import com.fitee.dto.RegistrationResponse;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import com.fitee.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
//    private final JwtProvider jwtProvider;


    /**
     * POST: Registers a new user in the database and sends a verification email.
     */
    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody User user) throws MessagingException {
        User newUser = userService.registerUser(user);
//        newUser.setLocked(true);
//        String jwtToken = jwtProvider.createVerifyingToken(newUser.getUsername());
//        userService.sendVerifyingEmail(user, jwtToken);
        return new RegistrationResponse(newUser.getEmail(), "User successfully registered");
    }

    /**
     * GET: Get all users
     */
    @GetMapping("")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * GET: Get SINGLE user by ID
     */
    @GetMapping(":id")
    public User getUserById(){
        return userRepository.findById(1l).get();
    }

    @GetMapping("/login")
    public String loginUser(){
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
