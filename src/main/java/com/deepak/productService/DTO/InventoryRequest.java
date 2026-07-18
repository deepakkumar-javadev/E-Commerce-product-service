package com.deepak.productService.DTO;

import lombok.Data;

@Data
public class InventoryRequest {

	private String skuCode;
	
	private Integer stockQuantity;
}
