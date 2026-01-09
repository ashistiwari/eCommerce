package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PostMapping("/api/public/category")
    public String createCategory(@RequestBody Category category){
        String response=categoryService.createCategory(category);
        return response ;
    }
    @DeleteMapping("/api/public/category/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        String response=categoryService.deleteCategory(categoryId);
        return response ;
    }
}
