package com.fitee.fiteeapp;

import com.fitee.fiteeapp.model.Category;
import com.fitee.fiteeapp.repository.CategoryRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(){
        Category newCategory = new Category("sporting");
        final Category save = categoryRepository.save(newCategory);
    }

}
