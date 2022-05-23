package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
