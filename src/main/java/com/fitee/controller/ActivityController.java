package com.fitee.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.model.Activity;
import com.fitee.model.Category;
import com.fitee.model.User;
import com.fitee.repository.CategoryRepository;
import com.fitee.service.ActivityService;
import com.fitee.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    /**
     * Retrieves all activities and returns them as a page object to support pagination.
     */
    @GetMapping
    public Page<Activity> search(@RequestParam(required = false) Map<String, String> queryMap,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "size", required = false) Integer size,
                                 Pageable pageable) {
        return activityService.searchAll(queryMap, pageable);
    }

    /**
     * POST: Creates a new activity in the database.
     */
    //@Secured(RoleType.SUPPLIER)
    @PostMapping
    public void createActivity(@RequestBody ObjectNode queryMap) {
        activityService.save(queryMap);
    }

    /**
     * GET: Retrieves a single activity based on its given id.
     */
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable long id) {
        Activity activity = activityService.findById(id);
        return activity;
    }

    /**
     * PUT: Updates a single product based on its given id.
     */
    //@Secured(RoleType.SUPPLIER)
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ObjectNode product) {
        activityService.update(id, product);
    }

    /**
     * DELETE: Deletes a single activity based on its given id.
     */
    //@Secured(RoleType.SUPPLIER)
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable long id) {
        activityService.deleteById(id);
    }

    /**
     * Retrieves all activity categories.
     *
     * @return All Categories
     */
    @GetMapping("/categories")
    public List<Category> getActivityCategories() {
        return categoryRepository.findAll();
    }





    @GetMapping("/userActivities")
    public List<Activity> getUserActivity() {
        User user = userService.getCurrentUser();

        return user.getOwnedActivities();
    }

}
