package com.fitee.fiteeapp.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.exception.ResourceAlreadyExistsException;
import com.fitee.fiteeapp.model.Activity;
import com.fitee.fiteeapp.model.RoleEntity;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.repository.RoleRepository;
import com.fitee.fiteeapp.repository.UserRepository;
import com.fitee.fiteeapp.service.construction.UserServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Spring Security Method for loading user
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        log.info("User found in the database: {}", email);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getName())));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user with email {} to the database", user.getEmail());
        // Check whether user with this Email already exist
        final User byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail != null) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleEntity userRoleEntity = roleRepository.findByName(user.getUserRole());
        user.getRoles().add(userRoleEntity);

        return userRepository.save(user);
    }

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRepository.findByEmail(email);
        RoleEntity role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUserByMail(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getCurrentUser() {
//        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return getUserByMail((String) principal);
        return getUserByMail("myEmail@email.com");
    }

    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    public boolean deleteById(long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User updateUser(long id,ObjectNode queryMap) {

        // Find the product to update with the ID
        User userToUpdate = userRepository.findById(id).get();

        userToUpdate.setFirstName(queryMap.get("firstName").asText());
        userToUpdate.setLastName(queryMap.get("lastName").asText());

        // Return the updated Product
        return userRepository.save(userToUpdate);
    }
}
