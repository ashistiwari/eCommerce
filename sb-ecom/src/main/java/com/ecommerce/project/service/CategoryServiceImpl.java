package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    private Long nextId=1L;
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new APIException("Category not created till now!!");
        }
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        Category savedCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory!=null){
            throw new APIException("Category already exists with category Name  : "+category.getCategoryName());
        }
        category.setCategoryId(nextId++);
        categoryRepository.save(category);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories=categoryRepository.findAll();
        Category categoryFetched= categories.stream().filter(category->category.getCategoryId().equals(categoryId)).findFirst()
                    .orElseThrow(()->new ResourceNotFoundException(
                            "Category","categoryId",categoryId));

        categoryRepository.delete(categoryFetched);
        return "Category with category id"+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        List<Category> categories=categoryRepository.findAll();
        Optional<Category> categoryFound=categories.stream().filter(category1->category1.getCategoryId().equals(categoryId)).findFirst();
        if(categoryFound.isPresent()){
            categoryFound.get().setCategoryName(category.getCategoryName());
            Category savedCategory=categoryRepository.save(categoryFound.get());
            return savedCategory;
        }else{
            throw  new ResourceNotFoundException(
                    "Category","categoryId",categoryId);
        }
    }
}
