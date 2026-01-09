package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {


    private List<Category> categories=new ArrayList<>();
    private Long nextId=1L;
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category categoryFetched= categories.stream().filter(category->category.getCategoryId().equals(categoryId)).findFirst()
                    .orElse(null);
        if (categoryFetched==null){
            return "Category not found";
        }
        categories.remove(categoryFetched);
        return "Category with category id"+categoryId+" deleted successfully";
    }
}
