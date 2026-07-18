package com.deepak.productService.DTO;

import lombok.Data;

@Data
public class productResponseUser {

	private String name;
	private double price;
	private String brand;
	private String color;
	private String size;
	private String category;
	private String availabilitystatus;
	private int stockQuantity;
	private String discription;
}
