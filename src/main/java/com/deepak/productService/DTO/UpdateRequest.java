package com.deepak.productService.DTO;

import lombok.Data;

@Data
public class UpdateRequest {


	private String name;
	private double price;
	private String brand;
	private String color;
	private String size;
	private String category;
	private String description;
}
