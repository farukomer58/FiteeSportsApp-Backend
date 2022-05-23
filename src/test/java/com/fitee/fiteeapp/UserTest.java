package com.fitee.fiteeapp;

import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test the user service and repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
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
        assertEquals(defaultTestUserEmail,user.getEmail());
    }

//    @Test()
//    public void loadNonExistingUserByEmail_ThrowException(){
//        String nonExistingUserEmail = "nonExisting@email.com";
//        try{
//            final UserDetails userDetails = userService.loadUserByUsername(nonExistingUserEmail);
//        } catch (UsernameNotFoundException e) {
//            assertThrows(UsernameNotFoundException.class,()-> {throw new UsernameNotFoundException(e.getMessage()}));
//        }
//    }

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
