package com.fitee.fiteeApp.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeApp.model.Booking;
import com.fitee.fiteeApp.model.Category;
import com.fitee.fiteeApp.model.User;
import com.fitee.fiteeApp.repository.ActivityRepository;
import com.fitee.fiteeApp.repository.BookingRepository;
import com.fitee.fiteeApp.repository.CategoryRepository;
import com.fitee.fiteeApp.service.construction.CategoryServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryServiceInterface {
    private final CategoryRepository categoryRepository;
    private final ActivityRepository activityRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public Category createCategory(ObjectNode queryMap) {
        Category category = new Category();
        category.setCategory(queryMap.get("category").asText());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(ObjectNode queryMap) {
        if (queryMap.get("id").isNull()) {
            // TODO: Throw Exception?
            return null;
        }

        Category categoryToUpdate = categoryRepository.findById(queryMap.get("id").asLong()).get();
        categoryToUpdate.setCategory(queryMap.get("category").asText());
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public Boolean deleteCategoryById(Long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
