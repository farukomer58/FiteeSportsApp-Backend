package com.fitee.fiteeApp.service;

import com.fitee.fiteeApp.dto.ActivityDateDto;
import com.fitee.fiteeApp.exception.ResourceNotFoundException;
import com.fitee.fiteeApp.model.Activity;
import com.fitee.fiteeApp.model.ActivityDate;
import com.fitee.fiteeApp.repository.ActivityDateRepository;
import com.fitee.fiteeApp.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ActivityDateService {

    private final ActivityRepository activityRepository;
    private final ActivityDateRepository activityDateRepository;


    /**
     * Find a activity dates by ID
     *
     * @param activityId The id of the activity you want all activity dates
     * @return The found activity dates for the given actibity
     */
    public List<ActivityDate> getAllActivityDates(long activityId) {

        final Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new ResourceNotFoundException("No Activity Dates found with activity id: " + activityId));

        return activity.getActivityDates();
    }

    /**
     * Create and Save the acitivity Date to the database and link with the acititvy,
     *
     * @param activityId, date The activityDate received From the Frontend / User
     */
    public ActivityDate save(long activityId, ActivityDateDto activityDateDto) {

        Activity activity = activityRepository.findById(activityId).get();
        ActivityDate activityDate = new ActivityDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(activityDateDto.getDate(), formatter);
        activityDate.setDate(dateTime);
        activityDate.setMaxParticipants(activityDateDto.getMaxParticipant());

        activity.getActivityDates().add(activityDate);
        activityDate.setActivity(activity);

        System.out.println("Activity Date added, The final Product: ");
        return activityDateRepository.save(activityDate);
    }

    /**
     * Update ActivityDate By Id
     *
     * @param id the activityDate ID
     * @return The updated ActivityDate
     */
    public ActivityDate update(long id, LocalDateTime newDate) {

        // Find the product to update with the ID
        ActivityDate newUpdatedActivity = activityDateRepository.findById(id).get();

        // Set the values of the product
        newUpdatedActivity.setDate(newDate);
        // Return the updated Product
        return activityDateRepository.save(newUpdatedActivity);
    }

    /**
     * Delete a Activity by ID
     *
     * @param id The id of the product
     */
    public void deleteById(long id) {
        // TODO: allow only activity removals belonging to user.
        activityDateRepository.deleteById(id);
    }

    /**
     * Find all activity Dates
     *
     * @return The activities Dates of all Activities
     */
    public List<ActivityDate> getAllDates() {
        return activityDateRepository.findAll();
    }

}
