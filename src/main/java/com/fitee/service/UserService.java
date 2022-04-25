package com.fitee.service;


import com.fitee.exception.ResourceAlreadyExistsException;
import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    // Dependencies
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JavaMailSender javaMailSender;
//    private final JwtProvider jwtProvider;

    /**
     * Retrieves the supplier-info with a given supplier-id. If not found throws a
     * ResourceNotFoundException indicating that the supplier-info could not be found.
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found" +
                " with id: " + id));
    }

    /**
     * Registers a new user to the database. New users containing an already owned username are
     * ignored and thrown with a ResourceAlreadyExistsException indicating duplication of a given
     * resource (e.g. not unique username).
     */
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("An existing resource was found");
        }
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLocked(0);

        User save = userRepository.save(user);
        return save;
    }

    /**
     * Retrieves the current context user from the database. Throws a ResourceNotFoundException if
     * the user entity could not be found in the database.
     */
    public User getCurrentUser() {
        return userRepository.findById(2l).get();
    }
}
