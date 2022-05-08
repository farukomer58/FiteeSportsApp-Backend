package com.fitee.fiteeApp;

import com.fitee.fiteeApp.model.RoleEntity;
import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FiteeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiteeAppApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {

            userService.saveRole(new RoleEntity(null, "CUSTOMER"));
            userService.saveRole(new RoleEntity(null, "FREELANCER"));

            userService.saveUser(new User("John", "I dont know", "myEmail@email.com", "password"));
            userService.saveUser(new User("saddas", "kasan", "myEmail2@email.com", "password"));
            userService.saveUser(new User("hasan", "Basan", "myEmail3@email.com", "password"));

            userService.addRoleToUser("myEmail@email.com", "CUSTOMER");
            userService.addRoleToUser("myEmail@email.com", "FREELANCER");
            userService.addRoleToUser("myEmail2@email.com", "CUSTOMER");
            userService.addRoleToUser("myEmail3@email.com", "FREELANCER");
        };
    }
}
