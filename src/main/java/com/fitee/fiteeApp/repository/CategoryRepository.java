package com.fitee.fiteeApp.repository;

import com.fitee.fiteeApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
