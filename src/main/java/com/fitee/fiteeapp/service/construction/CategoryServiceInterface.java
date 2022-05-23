package com.fitee.fiteeapp.service.construction;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fitee.fiteeapp.model.Category;

import java.util.List;

public interface CategoryServiceInterface {

    /**
     * Get All available categories
     * @return all categories
     */
    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);

    /**
     * Create and Save the Category to the database
     *
     * @param queryMap The JSON category data received From the Frontend / User
     * @return created Category
     */
    Category createCategory(ObjectNode queryMap);

    /**
     * Update the Category and save to the database
     *
     * @param queryMap The JSON category data received From the Frontend / User
     * @return updated Category
     */
    Category updateCategory(ObjectNode queryMap);

    /**
     * Deletes Category By Id
     * @param categoryId
     * @return true when deleted false when deletion failed
     */
    Boolean deleteCategoryById(Long categoryId);
}
