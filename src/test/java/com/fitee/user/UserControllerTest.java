package com.fitee.user;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import com.fitee.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @author Omer Citik
 * Test the User controller and repository
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;  // Used for doing requests to end-points

    @Autowired
    private UserService userService;

    /** Get All Users Test */
    @Test
    public void getUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    /**
     * Retrieve first supplier with the use of the supplierservice/repository.
     * Update some data and try to update the supplier with the use of the controller without an image
     * And when done with the test reset all data as it was, so we dont actually change the data on the system
     */
    @Test
    @DirtiesContext
    void registerCustomerTest() throws Exception {

        // Create new User
        User newUser = new User();
        newUser.setId(10001l);
        newUser.setEmail("test@test.com");
        newUser.setFirstName("testName");
//        newUser.setBirthDate(LocalDateTime.from(LocalDate.now()));

        try {
            // Do a POST request for creating the user
//            final ResultActions newUser1 =
//                    mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").accept(MediaType.APPLICATION_JSON).requestAttr("newUser", newUser));
//            newUser1.andExpect(status().isOk());

            HttpEntity<User> httpEntity = new HttpEntity<>(newUser);
            final ResponseEntity<Boolean> exchange = this.restTemplate.exchange("/api/v1/users", HttpMethod.POST,
                    httpEntity, Boolean.class);

            System.out.println(exchange.getStatusCode());
            assertTrue((exchange.getStatusCode().equals(status().isOk())));

            User retrievedUser =userService.findById(10001l);
            assertTrue(retrievedUser.getEmail().endsWith("test@test.com"));
        } catch (Exception e) {
            // If we come here we know the update failed and then we also fail the entire test
            throw new Exception(e);
            /*assertFalse(true);*/
        }
    }


}
