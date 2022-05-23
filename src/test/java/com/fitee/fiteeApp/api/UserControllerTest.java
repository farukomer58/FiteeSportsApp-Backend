package com.fitee.fiteeApp.api;

import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the user  controller and api
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private TestRestTemplate restTemplate;  // Used for doing requests to end-points


    @Test
    public void registerUser() throws URISyntaxException {

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/users/register";
        URI uri = new URI(baseUrl);
        User user = new User("Test", "User", "test@test.com", "test", "CUSTOMER");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        assertEquals(201, result.getStatusCodeValue());
    }


}
