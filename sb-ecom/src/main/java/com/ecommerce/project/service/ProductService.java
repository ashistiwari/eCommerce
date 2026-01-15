package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

    public ProductDto addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
