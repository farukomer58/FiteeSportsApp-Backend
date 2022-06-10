package com.fitee.fiteeapp;

import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test the user service and repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
//@DataJpaTest // Runs test/application.properties database
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserById_Test() {
        // Arrange
        String defaultTestUserEmail = "myEmail@email.com";
        // Act
        User user = userService.findById(1l);
        // Assert
        assertEquals(defaultTestUserEmail, user.getEmail());
    }

    @Test()
    public void loadNonExistingUserByEmail_ThrowException() {
        String nonExistingUserEmail = "nonExisting@email.com";
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingUserEmail));
//            assertEquals(exception.getClass(), UsernameNotFoundException.class);
    }

    @Test
    @DirtiesContext // restores values
    public void deleteUserById_True() {
        // Arrange
        Long userIdToDelete = Long.valueOf(1);
        // Act
        boolean isUserDeleted = userService.deleteById(userIdToDelete);
        // Assert
        assertTrue(isUserDeleted);
    }

}
