package com.fitee.fiteeapp;

import com.fitee.fiteeapp.service.ActivityService;
import com.fitee.fiteeapp.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test the Activity service and repository
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ActivityTest {

    @Autowired
    private ActivityService activityService;




}
