package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",categoryId));
        product.setImage("Image.png");
        product.setCategory(category);
        double specialPrice=product.getPrice()-((product.getDiscount()*0.01)* product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct=productRepository.save(product);
        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
        return savedProductDto;

    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productsDTOS = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDto> productsDTOS = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");
        List<ProductDto> productsDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDTOS);
        return productResponse;
    }

    @Override
    public ProductDto upadteProducts(Long productId, Product product) {
        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product1.setProductName(product.getProductName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setDiscount(product.getDiscount());
        product1.setSpecialPrice(product.getSpecialPrice());
        product1.setQuantity(product.getQuantity());
        product1.setImage(product.getImage());
        Product updatedProduct = productRepository.save(product1);
        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
        return updatedProductDto;

    }


}

