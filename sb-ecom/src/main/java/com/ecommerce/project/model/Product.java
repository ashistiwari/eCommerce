package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String description;
    private String productName;
    private Integer quantity;
    private double price;
    private double discount;
    @Column(nullable = true)
    private Double specialPrice;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
