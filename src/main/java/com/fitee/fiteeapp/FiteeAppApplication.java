package com.fitee.fiteeapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.RoleEntity;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.service.ActivityService;
import com.fitee.fiteeapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class FiteeAppApplication implements CommandLineRunner {

    private final UserService userService;
    private final ActivityService activityService;

    public static void main(String[] args) {
        SpringApplication.run(FiteeAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Save Default Roles
        userService.saveRole(new RoleEntity(null, "CUSTOMER"));
        userService.saveRole(new RoleEntity(null, "FREELANCER"));

        // Save Default User
        userService.saveUser(new User("John", "I dont know", "myEmail@email.com", "password", "CUSTOMER"));
        userService.saveUser(new User("saddas", "kasan", "myEmail2@email.com", "password", "CUSTOMER"));
        userService.saveUser(new User("hasan", "Basan", "myEmail3@email.com", "password", "FREELANCER"));

        // Save Default Activities
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        root.put("title", "Default Activity Title");
        root.put("description", "Activity description default");
        root.put("ownerName", "");
        root.put("activityAddress", "");
        root.put("city", "default");
        root.put("prices", (JsonNode) null);
        root.put("categories", (JsonNode) null);
        root.put("activityDates", (JsonNode) null);

        activityService.save(root);

        // TODO: Create Database Tables with TEST prefix and use Test database for testing
    }
}
