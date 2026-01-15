package com.ecommerce.project.controller;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/admin/category/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product ,
                                                 @PathVariable Long categoryId){
        ProductDto productDto=productService.addProduct(categoryId,product);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
@GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse=productService.getAllProducts();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse=productService.searchByCategoryId(categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
}
