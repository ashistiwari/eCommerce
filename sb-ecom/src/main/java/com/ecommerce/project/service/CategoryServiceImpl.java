package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategororyResponse;
import com.ecommerce.project.payload.CategoryDTORequest;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    private Long nextId=1L;
    @Override
    public CategororyResponse getAllCategories(Integer pageNumber , Integer pageSize) {
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories=categoryPage.getContent();
        List<CategoryDTORequest> collect = categories.stream().map(category -> modelMapper.map(category, CategoryDTORequest.class)).collect(Collectors.toList());
        if (categories.isEmpty()){
            throw new APIException("Category not created till now!!");
        }
        CategororyResponse categororyResponse=new CategororyResponse();
        categororyResponse.setContent(collect);
        return categororyResponse;
    }

    @Override
    public CategoryDTORequest createCategory(CategoryDTORequest categoryDTO) {
        Category cateory= modelMapper.map(categoryDTO, Category.class);
        Category savedCategory=categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (savedCategory!=null){
            throw new APIException("Category already exists with category Name  : "+categoryDTO.getCategoryName());
        }

        Category savedCategory1= categoryRepository.save(cateory);
        return modelMapper.map(savedCategory1, CategoryDTORequest.class);
    }

    @Override
    public CategoryDTORequest deleteCategory(Long categoryId) {
        List<Category> categories=categoryRepository.findAll();
        Category categoryFetched= categories.stream().filter(category->category.getCategoryId().equals(categoryId)).findFirst()
                    .orElseThrow(()->new ResourceNotFoundException(
                            "Category","categoryId",categoryId));
        categoryRepository.delete(categoryFetched);
        return modelMapper.map(categoryFetched,CategoryDTORequest.class);
    }

    @Override
    public CategoryDTORequest updateCategory(Long categoryId, Category category) {
        List<Category> categories=categoryRepository.findAll();
        Optional<Category> categoryFound=categories.stream().filter(category1->category1.getCategoryId().equals(categoryId)).findFirst();
        if(categoryFound.isPresent()){
            categoryFound.get().setCategoryName(category.getCategoryName());
            Category savedCategory=categoryRepository.save(categoryFound.get());
            CategoryDTORequest categoryDto = modelMapper.map(savedCategory, CategoryDTORequest.class);
            return categoryDto;
        }else{
            throw  new ResourceNotFoundException(
                    "Category","categoryId",categoryId);
        }
    }
}
