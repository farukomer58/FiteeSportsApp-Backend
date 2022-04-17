package com.fitee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.dto.ActivityDto;
import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.Activity;
import com.fitee.model.User;
import com.fitee.repository.ActivityRepository;
import com.fitee.search.ActivitySpecification;
import lombok.AllArgsConstructor;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
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
        var productPage = activityRepository.findAll(new ActivitySpecification(queryMap), pageable);
        return productPage.map((Activity activity) -> {
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
        activity.setPrice(queryMap.get("price").decimalValue());
        activity.setCreatedDate(LocalDateTime.now());

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
        newUpdatedActivity.setPrice(product.get("price").decimalValue());
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
