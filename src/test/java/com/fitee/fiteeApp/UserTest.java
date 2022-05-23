package com.fitee.fiteeApp;

import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Test the user service and repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void findById_Test() {
        // Arrange
        String defaultTestUserEmail = "myEmail@email.com";
        // Act
        User user = userService.findById(1l);
        // Assert
        assertEquals(defaultTestUserEmail,user.getEmail());
    }

}
