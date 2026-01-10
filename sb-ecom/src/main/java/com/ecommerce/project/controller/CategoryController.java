package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String createCategory(@Valid @RequestBody Category category){
        String response=categoryService.createCategory(category);
        return response ;
    }
    @DeleteMapping("/api/public/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String response = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
    @PutMapping("/api/public/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                 @Valid @RequestBody Category category){
        try {
            Category categoryUpdated = categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(categoryUpdated, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(category,e.getStatusCode());
        }

    }
}
