package com.fitee.fiteeapp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.Category;
import com.fitee.fiteeapp.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> retrieveAllCategories() {
        final List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(allCategories);
    }

    @GetMapping("/get")
    public ResponseEntity<Category> retrieveCategoryById(@RequestParam Long categoryId) {
        final Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@RequestBody ObjectNode queryMap) {
        final Category savedCategory = categoryService.createCategory(queryMap);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/activities/categories/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();
        return ResponseEntity.created(location).body(savedCategory);
    }

    @PutMapping("")
    public ResponseEntity<Category> updateCategory(@RequestBody ObjectNode queryMap) {
        final Category updatedCategory = categoryService.updateCategory(queryMap);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/activities/categories/{id}")
                .buildAndExpand(updatedCategory.getId()).toUri();
        return ResponseEntity.created(location).body(updatedCategory);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> removeCategoryById(@RequestParam Long categoryId){
        final boolean removed = categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().body(removed);
    }

}
