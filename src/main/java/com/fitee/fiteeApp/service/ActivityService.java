package com.fitee.fiteeApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fitee.fiteeApp.dto.ActivityDateDto;
import com.fitee.fiteeApp.exception.ResourceNotFoundException;
import com.fitee.fiteeApp.model.*;
import com.fitee.fiteeApp.repository.ActivityDateRepository;
import com.fitee.fiteeApp.repository.ActivityPriceRepository;
import com.fitee.fiteeApp.repository.ActivityRepository;
import com.fitee.fiteeApp.repository.CategoryRepository;
import com.fitee.fiteeApp.search.ActivitySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityPriceRepository activityPriceRepository;
    private final ActivityDateRepository activityDateRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

//    private final ImageRepository imageRepository;
//    private final ModelMapper modelmapper;
//    private final DiscountPriceRepository discountPriceRepository;

    /**
     * Find a activity by ID
     *
     * @return The activities in pageable form
     */
    public Page<Activity> searchAll(Map<String, String> queryMap, Pageable pageable) {
        final Page<Activity> activityPage = activityRepository.findAll(new ActivitySpecification(queryMap), pageable);
        return activityPage.map((Activity activity) -> {
            return activity;
        });
    }

    public List<Activity> getOwnActivities() {
        final User currentUser = userService.getCurrentUser();
        return currentUser.getOwnedActivities();
    }

    /**
     * Find a activity by ID
     *
     * @param id The id of the activity you want
     * @return The found activity
     */
    public Activity findById(long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Activity not found with id: " + id));
    }

    /**
     * Delete a Activity by ID
     *
     * @param id The id of the product
     */
    public void deleteById(long id) {
        // TODO: allow only activity removals belonging to user.
        activityRepository.deleteById(id);
    }

    /**
     * Create and Save the acitivity to the database,
     *
     * @param queryMap The JSON activityData received From the Frontend / User
     */
    public Activity save(ObjectNode queryMap) {

        Activity activity = new Activity();

        activity.setTitle(queryMap.get("title").asText());
        activity.setDescription(queryMap.get("description").asText());
        activity.setCreatedDate(LocalDateTime.now());

        for (JsonNode prices : queryMap.get("prices")) {
            System.out.println(prices);

            final JsonNode lessons = prices.get("lessons");
            final JsonNode price = prices.get("price");
            final JsonNode discount = prices.get("discount");
            ActivityPrice activityPrice = new ActivityPrice(Integer.valueOf(lessons.asInt()),
                    BigDecimal.valueOf(price.asDouble()), BigDecimal.valueOf(discount.asDouble()));
            final ActivityPrice savedActivityPrice = activityPriceRepository.save(activityPrice);

            activity.getActivityPrices().add(savedActivityPrice);
            activityRepository.save(activity);
            savedActivityPrice.setActivity(activity);
        }

        if (!queryMap.get("categories").isNull()) {
            for (JsonNode category : queryMap.get("categories")) {
                final Category categoryToAdd = categoryRepository.findById(category.asLong()).orElseThrow();
                activity.getCategories().add(categoryToAdd);
                categoryToAdd.getActivities().add(activity);
            }
        }

        if (!queryMap.get("activityDates").isNull()) {
            for (JsonNode activityDate : queryMap.get("activityDates")) {
                ActivityDate activityDateObject = new ActivityDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(activityDate.get("date").asText(), formatter);
                activityDateObject.setDate(dateTime);
                activityDateObject.setMaxParticipants(activityDate.get("maxParticipants").asInt());
                final ActivityDate savedActivityDate = activityDateRepository.save(activityDateObject);
                System.out.println(savedActivityDate);

                activity.getActivityDates().add(savedActivityDate);
                activityRepository.save(activity);
                savedActivityDate.setActivity(activity);
            }
        }


        User user = userService.getCurrentUser();
        activity.setOwner(user);
        user.addOwnedActivities(activity);

        System.out.println("Activity added, The final Product: ");
        System.out.println(activity);
        return activityRepository.save(activity);
    }

    /**
     * Update Activity By Id
     *
     * @param id       the activity ID
     * @param queryMap The updated Activity values
     * @return The updated Activity
     */
    public Activity update(long id, ObjectNode queryMap) {

        // Find the product to update with the ID
        Activity newUpdatedActivity = activityRepository.findById(id).get();

        newUpdatedActivity.setTitle(queryMap.get("title").asText());
        newUpdatedActivity.setDescription(queryMap.get("description").asText());
        newUpdatedActivity.setCreatedDate(LocalDateTime.now());

        if (!queryMap.get("prices").isNull()) {
            for (JsonNode prices : queryMap.get("prices")) {
                System.out.println(prices);

                final JsonNode lessons = prices.get("lessons");
                final JsonNode price = prices.get("price");
                final JsonNode discount = prices.get("discount");
                ActivityPrice activityPrice = new ActivityPrice(Integer.valueOf(lessons.asInt()),
                        BigDecimal.valueOf(price.asDouble()), BigDecimal.valueOf(discount.asDouble()));
                final ActivityPrice savedActivityPrice = activityPriceRepository.save(activityPrice);

                newUpdatedActivity.getActivityPrices().add(savedActivityPrice);
                activityRepository.save(newUpdatedActivity);
                savedActivityPrice.setActivity(newUpdatedActivity);
            }
        }

        if (!queryMap.get("categories").isNull()) {
            for (JsonNode category : queryMap.get("categories")) {
                final Category categoryToAdd = categoryRepository.findById(category.asLong()).orElseThrow();
                newUpdatedActivity.getCategories().add(categoryToAdd);
                categoryToAdd.getActivities().add(newUpdatedActivity);
            }
        }

        if (!queryMap.get("activityDates").isNull()) {
            for (JsonNode activityDate : queryMap.get("activityDates")) {
                ActivityDate activityDateObject = new ActivityDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(activityDate.get("date").asText(), formatter);
                activityDateObject.setDate(dateTime);
                activityDateObject.setMaxParticipants(activityDate.get("maxParticipants").asInt());
                final ActivityDate savedActivityDate = activityDateRepository.save(activityDateObject);
                System.out.println(savedActivityDate);

                newUpdatedActivity.getActivityDates().add(savedActivityDate);
                activityRepository.save(newUpdatedActivity);
                savedActivityDate.setActivity(newUpdatedActivity);
            }
        }

        // Return the updated Product
        return activityRepository.save(newUpdatedActivity);
    }

    /**
     * Create and Save the acitivity Date to the database and link with the acititvy,
     *
     * @param activityId, date The activityDate received From the Frontend / User
     */
    public ActivityDate saveActivityDate(long activityId, ActivityDateDto activityDateDto) {

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


}
