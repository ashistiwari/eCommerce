package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategororyResponse;
import com.ecommerce.project.payload.CategoryDTORequest;
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
    public ResponseEntity<CategororyResponse> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
    @PostMapping("/api/public/category")
    public ResponseEntity<CategoryDTORequest> createCategory(@Valid @RequestBody CategoryDTORequest category){
        CategoryDTORequest savedCategoryDTO =categoryService.createCategory(category);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED) ;
    }
    @DeleteMapping("/api/public/category/{categoryId}")
    public ResponseEntity<CategoryDTORequest> deleteCategory(@PathVariable Long categoryId){

            CategoryDTORequest response = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @PutMapping("/api/public/category/{categoryId}")
    public ResponseEntity<CategoryDTORequest> updateCategory(@PathVariable Long categoryId,
                                                 @Valid @RequestBody Category category){

            CategoryDTORequest categoryUpdated = categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(categoryUpdated, HttpStatus.OK);

    }
}
