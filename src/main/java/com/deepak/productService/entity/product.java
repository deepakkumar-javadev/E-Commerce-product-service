package com.deepak.productService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "MacyProducts")
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

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
