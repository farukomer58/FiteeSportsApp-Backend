package com.fitee.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.dto.ActivityDto;
import com.fitee.model.Activity;
import com.fitee.model.ActivityType;
import com.fitee.repository.ActivityTypeRepository;
import com.fitee.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/activity")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityTypeRepository activityTypeRepository;

    /**
     * Retrieves all activities and returns them as a page object to support pagination.
     */
    @GetMapping("")
    public List<Activity> search() {
        return activityService.searchAll();
    }


    /**
     * Creates a new product in the database.
     */
//    @Secured(RoleType.SUPPLIER)
    @PostMapping
    public void createProduct(@RequestBody ObjectNode queryMap) {
        activityService.save(queryMap);
    }

    /**
     * Retrieves a single product based on its given id.
     */
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable long id) {
        Activity product = activityService.findById(id);
        return product;
    }

    /**
     * Deletes a single product based on its given id.
     */
//    @Secured(RoleType.SUPPLIER)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        activityService.deleteById(id);
    }

    /**
     * Updates a single product based on its given id.
     */
//    @Secured(RoleType.SUPPLIER)
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ObjectNode product) {
        activityService.update(id, product);
    }

    /**
     * Retrieves all product categories.
     */
    @GetMapping("/categories")
    public List<ActivityType> getProductCategories() {
        return activityTypeRepository.findAll();
    }


}
