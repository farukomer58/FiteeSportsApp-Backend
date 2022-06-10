package com.fitee.fiteeapp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.dto.ActivityDateDto;
import com.fitee.fiteeapp.model.Activity;
import com.fitee.fiteeapp.model.ActivityDate;
import com.fitee.fiteeapp.model.Category;
import com.fitee.fiteeapp.model.User;
import com.fitee.fiteeapp.repository.ActivityRepository;
import com.fitee.fiteeapp.repository.CategoryRepository;
import com.fitee.fiteeapp.service.ActivityService;
import com.fitee.fiteeapp.service.CategoryService;
import com.fitee.fiteeapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    /**
     * Retrieves all activities and returns them as a page object to support pagination.
     */
    @GetMapping("")
    public Page<Activity> search(@RequestParam(required = false) Map<String, String> queryMap,
                                 @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        return activityService.searchAll(queryMap, pageable);
    }

    /**
     * Retrieves all activities of the logged in User
     */
    @GetMapping("/own")
    public List<Activity> searchOwnActivities() {
        return activityService.getOwnActivities();
    }

    /**
     * GET: Retrieves a single activity based on its given id.
     */
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable long id) {
        return activityService.findById(id);
    }

    /**
     * POST: Creates a new activity in the database.
     */
    @PostMapping("")
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
        return activityService.saveActivityDate(activityId, activityDateDto);
    }

    @GetMapping("/userActivities")
    public List<Activity> getUserActivity() {
        User user = userService.getCurrentUser();
        return user.getOwnedActivities();
    }

}
