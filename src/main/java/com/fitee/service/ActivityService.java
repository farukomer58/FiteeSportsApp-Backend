package com.fitee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.dto.ActivityDto;
import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.Activity;
import com.fitee.model.User;
import com.fitee.repository.ActivityRepository;
import com.fitee.repository.ActivityTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ActivityService {

    private final EntityManager entityManager;
    private final ActivityRepository activityRepository;
    //    private final ModelMapper modelmapper;
    private final ActivityTypeRepository activityTypeRepository;
    private final UserService userService;
//    private final ImageRepository imageRepository;
//    private final DiscountPriceRepository discountPriceRepository;


    public List<Activity> searchAll() {
        return activityRepository.findAll();
    }

    /**
     * Find a product by ID
     *
     * @param id The id of the activity you want
     * @return The found product
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
        // TODO: allow only product removals belonging to user.
        activityRepository.deleteById(id);
    }

    /**
     * Create and Save the product to the database, we also check for a Product Image if provided
     * And also Product Discounnt if provided
     *
     * @param queryMap The JSON productData received From the Frontend / User
     */
    public Activity save(ObjectNode queryMap) {

        Activity activity = new Activity();

        activity.setTitle(queryMap.get("title").asText());
        activity.setPrice(queryMap.get("price").decimalValue());
//        product.setQuantity(queryMap.get("stock").asDouble());
//        product.setUnit(queryMap.get("unit").asText());
//        long categoryId = Long.parseLong(queryMap.get("category").asText());
//        product.addProductCategory(productCategoryRepository.getOne(categoryId));
//        product.setDescription(queryMap.get("description").asText());

        User user = userService.getCurrentUser();
        activity.setOwner(user);
        activity.setCreatedDate(LocalDateTime.now());


        System.out.println("Product added, The final Product: ");
        System.out.println(activity);
        return activityRepository.save(activity);
    }

    /**
     * Update and Save a Prodcut By Id
     *
     * @param id      the product ID
     * @param product The updated Product values
     * @return The updated Product
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
