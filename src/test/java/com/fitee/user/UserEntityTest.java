package com.fitee.user;

import com.fitee.DemoApplication;
import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import com.fitee.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Omer Citik
 * Test the User Enity and service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserEntityTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /** Check if retrieved user has right attributes */
    @Test
    public void checkFirstUserEmailCorrectness() {
        User user = userService.findById(1l);
        assertEquals("of.c.58@hotmail.com", user.getEmail());
    }

    /**
     * Get User from service/repository that does not exist, check for right exception and response
     */
    @Test
    public void returnUserThatDoesNotExist() {
        long nonExistingSupplierId = -1;
        try {
            User retrievedUser = userService.findById(nonExistingSupplierId);
            // If the user is not null fail the test
            assertNull(retrievedUser);
        } catch (Exception e) {
            // Check if the thrown exception is equal to ResourceNotFoundException if user is not found
            assertTrue(e.getClass().equals(ResourceNotFoundException.class));

            // Check if the exception message is as expected
            Assertions.assertEquals("User not found with id: " + nonExistingSupplierId, e.getMessage());
        }
    }

    @Test
    @DirtiesContext // restores values
    void updateUserTest() {
        //Get Course
        User user = userService.findById(1l);
        // Update
        user.setFirstName("New First Name");
        userRepository.save(user);
        // check the value
        User updatedUser = userService.findById(1l);
        assertEquals("New First Name",updatedUser.getFirstName());
    }


}
