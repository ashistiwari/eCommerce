package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        categories.remove(categoryFetched);
        return "Category with category id"+categoryId+" deleted successfully";
    }

    @Override
    public String updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryFound=categories.stream().filter(category1->category1.getCategoryId().equals(categoryId)).findFirst();
        if(categoryFound.isPresent()){
            categoryFound.get().setCategoryName(category.getCategoryName());
            return "Category updated successfully";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
        }
    }
}
