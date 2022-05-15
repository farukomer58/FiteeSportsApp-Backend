package com.fitee.fiteeApp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeApp.dto.ActivityDateDto;
import com.fitee.fiteeApp.model.Activity;
import com.fitee.fiteeApp.model.ActivityDate;
import com.fitee.fiteeApp.model.Category;
import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.repository.CategoryRepository;
import com.fitee.fiteeApp.service.ActivityDateService;
import com.fitee.fiteeApp.service.ActivityService;
import com.fitee.fiteeApp.service.CategoryService;
import com.fitee.fiteeApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityDateService activityDateService;
    private final CategoryService categoryService;
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
     * GET: Retrieves a single activity based on its given id.
     */
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable long id) {
        Activity activity = activityService.findById(id);
        return activity;
    }

    /**
     * POST: Creates a new activity in the database.
     */
    //@Secured(RoleType.SUPPLIER)
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody ObjectNode queryMap) {
        final Activity savedActivity = activityService.save(queryMap);

        URI location =
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/activities/{id}").buildAndExpand(savedActivity.getId()).toUri();
        return ResponseEntity.created(location).body(savedActivity);
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


    @PostMapping("/addDate")
    public ActivityDate addActivityDate(@RequestBody ActivityDateDto activityDateDto, @RequestParam long activityId) {

        System.out.println(activityDateDto.getDate());

        final ActivityDate save = activityDateService.save(activityId, activityDateDto);
        return save;
    }

    @GetMapping("/userActivities")
    public List<Activity> getUserActivity() {
        User user = userService.getCurrentUser();
        return user.getOwnedActivities();
    }

}
