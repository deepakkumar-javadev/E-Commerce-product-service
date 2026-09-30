package com.deepak.productService.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Products",
indexes = {
        @Index(name = "idx_product_category", columnList = "category"),
        @Index(name = "idx_product_brand", columnList = "brand"),
        @Index(name = "idx_product_price", columnList = "price"),
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_created_at", columnList = "created_at")
    })
@Data
public class product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;   

	private String name;
	
	private double price;

	private String brand;

	private String category;

	private String size;
	
	private String skuCode;
	
	private String description;

	private String color;

	private double rating;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
