package com.fitee.fiteeapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fitee.fiteeapp.dto.ActivityDateDto;
import com.fitee.fiteeapp.exception.ResourceNotFoundException;
import com.fitee.fiteeapp.model.*;
import com.fitee.fiteeapp.repository.ActivityDateRepository;
import com.fitee.fiteeapp.repository.ActivityPriceRepository;
import com.fitee.fiteeapp.repository.ActivityRepository;
import com.fitee.fiteeapp.repository.CategoryRepository;
import com.fitee.fiteeapp.search.ActivitySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
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

    /**
     * Search activity by pageable
     *
     * @return The activities in pageable form
     */
    public Page<Activity> searchAll(Map<String, String> queryMap, Pageable pageable) {
        final Page<Activity> activityPage = activityRepository.findAll(new ActivitySpecification(queryMap), pageable);
        return activityPage.map((Activity activity) -> {

            StringBuilder relatedCategoriesString = new StringBuilder();
            final List<Category> activityCategories = activity.getCategories();
            System.out.println(activityCategories);
            if (activityCategories.size() > 0) {
                for (Category category : activityCategories) {
                    relatedCategoriesString.append(category.getCategory() + ",");
                }
                relatedCategoriesString.deleteCharAt(relatedCategoriesString.length()-1 );
                activity.setCategoryString(relatedCategoriesString.toString());
            }
            return activity;
        });
    }

    /**
     * Get All activities made by logged in user
     *
     * @return List with own Activities
     */
    public List<Activity> getOwnActivities() {
        final User currentUser = userService.getCurrentUser();
        return currentUser.getOwnedActivities();
    }

    /**
     * Find an activity by ID
     *
     * @param id The id of the activity you want
     * @return The found activity
     */
    public Activity findById(long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Activity not found with id: " + id));
    }

    /**
     * Delete an Activity by ID
     *
     * @param id The id of the product
     */
    public void deleteById(long id) {
        // TODO: allow only activity removals belonging to user.
        activityRepository.deleteById(id);
    }

    /**
     * Create and Save the activity to the database,
     *
     * @param queryMap The JSON activityData received From the Frontend / User
     */
    public Activity save(ObjectNode queryMap) {

        Activity activity = new Activity();

        activity.setTitle(queryMap.get("title").asText());
        activity.setDescription(queryMap.get("description").asText());
        activity.setActivityAddress(queryMap.get("activityAddress").asText());
        activity.setCity(queryMap.get("city").asText());
        activity.setCreatedDate(LocalDateTime.now());

        final JsonNode allPrices = queryMap.get("prices");
        if (!allPrices.isNull()) {
            for (JsonNode prices : allPrices) {
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
        }

        final JsonNode categories = queryMap.get("categories");
        if (!categories.isNull()) {
            for (JsonNode category : categories) {
                final Category categoryToAdd = categoryRepository.findById(category.asLong()).orElseThrow();
                activity.getCategories().add(categoryToAdd);
                categoryToAdd.getActivities().add(activity);
            }
        }

        final JsonNode activityDates = queryMap.get("activityDates");
        if (!activityDates.isNull()) {
            for (JsonNode activityDate : activityDates) {
                ActivityDate activityDateObject = new ActivityDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(activityDate.get("date").asText(), formatter);
                activityDateObject.setDate(dateTime);
                activityDateObject.setMaxParticipants(activityDate.get("maxParticipants").asInt());
                activityDateObject.setCurrentParticipants(0);
                final ActivityDate savedActivityDate = activityDateRepository.save(activityDateObject);

                activity.getActivityDates().add(savedActivityDate);
                activityRepository.save(activity);
                savedActivityDate.setActivity(activity);
            }
        }

        User user = userService.getCurrentUser();
        activity.setOwner(user);
        activity.setOwnerName(user.getFirstName() + " "+ user.getLastName());
        user.addOwnedActivities(activity);

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
        newUpdatedActivity.setActivityAddress(queryMap.get("activityAddress").asText());
        newUpdatedActivity.setCity(queryMap.get("city").asText());

        final JsonNode allPrices = queryMap.get("prices");
        if (!allPrices.isNull()) {

            for (ActivityPrice price: newUpdatedActivity.getActivityPrices()){
                activityPriceRepository.delete(price);
            }
            newUpdatedActivity.setActivityPrices(new ArrayList<>());

            for (JsonNode prices : allPrices) {

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

            for (Category category: newUpdatedActivity.getCategories()){
                categoryRepository.delete(category);
            }
            newUpdatedActivity.setCategories(new ArrayList<>());

            for (JsonNode category : queryMap.get("categories")) {
                final Category categoryToAdd = categoryRepository.findById(category.asLong()).orElseThrow();
                newUpdatedActivity.getCategories().add(categoryToAdd);
                categoryToAdd.getActivities().add(newUpdatedActivity);
            }
        }

        if (!queryMap.get("activityDates").isNull()) {

            for (ActivityDate date: newUpdatedActivity.getActivityDates()){
                activityDateRepository.delete(date);
            }
            newUpdatedActivity.setActivityDates(new ArrayList<>());

            for (JsonNode activityDate : queryMap.get("activityDates")) {
                ActivityDate activityDateObject = new ActivityDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(activityDate.get("date").asText(), formatter);
                activityDateObject.setDate(dateTime);
                activityDateObject.setMaxParticipants(activityDate.get("maxParticipants").asInt());
                final ActivityDate savedActivityDate = activityDateRepository.save(activityDateObject);

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

        return activityDateRepository.save(activityDate);
    }


}
