package com.fitee.fiteeApp.service;

import com.fitee.fiteeApp.exception.ResourceNotFoundException;
import com.fitee.fiteeApp.model.RoleEntity;
import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.repository.RoleRepository;
import com.fitee.fiteeApp.repository.UserRepository;
import com.fitee.fiteeApp.service.construction.UserServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.error("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(roleEntity -> {
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user with email {} to the database", user.getEmail());
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
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = getUserByMail((String) principal);
        return currentUser;
        //        return userRepository.findById(principal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Current user not found in database."));
    }
}
