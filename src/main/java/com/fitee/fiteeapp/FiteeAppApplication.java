package com.fitee.fiteeapp;

import com.fitee.fiteeapp.model.RoleEntity;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.service.UserService;
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

            final RoleEntity customer = userService.saveRole(new RoleEntity(null, "CUSTOMER"));
            final RoleEntity freelancer = userService.saveRole(new RoleEntity(null, "FREELANCER"));

            userService.saveUser(new User("John", "I dont know", "myEmail@email.com", "password","CUSTOMER"));
            userService.saveUser(new User("saddas", "kasan", "myEmail2@email.com", "password","CUSTOMER"));
            userService.saveUser(new User("hasan", "Basan", "myEmail3@email.com", "password","FREELANCER"));

//            userService.addRoleToUser("myEmail@email.com", "CUSTOMER");
//            userService.addRoleToUser("myEmail@email.com", "FREELANCER");
//            userService.addRoleToUser("myEmail2@email.com", "CUSTOMER");
//            userService.addRoleToUser("myEmail3@email.com", "FREELANCER");
        };
    }
}
