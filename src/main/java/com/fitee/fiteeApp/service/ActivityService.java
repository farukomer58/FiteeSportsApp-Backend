package com.fitee.fiteeApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fitee.fiteeApp.exception.ResourceNotFoundException;
import com.fitee.fiteeApp.model.Activity;
import com.fitee.fiteeApp.model.ActivityPrice;
import com.fitee.fiteeApp.model.Category;
import com.fitee.fiteeApp.model.User;
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
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityPriceRepository activityPriceRepository;
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
        }

        if(!queryMap.get("categories").isNull()){
            for (JsonNode category: queryMap.get("categories")){
                final Category categoryToAdd = categoryRepository.findById(category.get(0).asLong()).get();
                activity.getCategories().add(categoryToAdd);
                categoryToAdd.getActivities().add(activity);
            }
        }


//        product.setQuantity(queryMap.get("stock").asDouble());
//        product.setUnit(queryMap.get("unit").asText());
//        long categoryId = Long.parseLong(queryMap.get("category").asText());
//        product.addProductCategory(productCategoryRepository.getOne(categoryId));
//        product.setDescription(queryMap.get("description").asText());

        User user = userService.getCurrentUser();
        activity.setOwner(user);
        // user.addOwnedActivities(activity); Not necessary


        System.out.println("Activity added, The final Product: ");
        System.out.println(activity);
        return activityRepository.save(activity);
    }

    /**
     * Update Activity By Id
     *
     * @param id      the activity ID
     * @param product The updated Activity values
     * @return The updated Activity
     */
    public Activity update(long id, ObjectNode product) {

        // Find the product to update with the ID
        Activity newUpdatedActivity = activityRepository.findById(id).get();

        // Set the values of the product
        newUpdatedActivity.setTitle(product.get("title").asText());

        System.out.println(product.get("prices"));
//        newUpdatedProduct.setQuantity(product.get("stock").asDouble());
//        newUpdatedProduct.setUnit(product.get("unit").asText());
//        long categoryId = Long.parseLong(product.get("category").asText());
//        newUpdatedProduct.addProductCategory(productCategoryRepository.getOne(categoryId));
//        newUpdatedProduct.setDescription(product.get("description").asText());


        /*
        ProductEntity currentProduct = entityManager.getReference(ProductEntity.class, id);
        modelmapper.map(newUpdatedProduct, currentProduct); // new --> updateInto --> current

        System.out.println(currentProduct);*/

        // Return the updated Product
        return activityRepository.save(newUpdatedActivity);
    }

}
