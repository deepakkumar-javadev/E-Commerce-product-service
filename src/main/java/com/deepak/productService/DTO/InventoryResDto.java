package com.deepak.productService.DTO;

import lombok.Data;

@Data
public class InventoryResDto {

	private String skuCode;

	private String availablityStatus;

	private Integer stockQuantity;
}