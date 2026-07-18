package com.deepak.productService.DTO;

import lombok.Data;

// input DTO [user incoming data binding class]

@Data
public class productRequest {

	private String name;
	private double price;
	private String brand;
	private String color;
	private String size;
	private String category;
	private Integer stockQuantity;
	
}
