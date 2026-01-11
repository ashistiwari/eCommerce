package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategororyResponse;
import com.ecommerce.project.payload.CategoryDTORequest;

import java.util.List;

public interface CategoryService {
    CategororyResponse getAllCategories();
    CategoryDTORequest createCategory(CategoryDTORequest category);
    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
