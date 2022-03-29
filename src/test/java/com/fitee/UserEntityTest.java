package com.fitee;

import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void simpleTest() {
        User user = userRepository.findById(1l).get();

        assertEquals("of.c.58@hotmail.com", user.getEmail());

    }

}
