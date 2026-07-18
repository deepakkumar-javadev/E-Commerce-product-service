package com.deepak.productService.DTO;

import lombok.Data;

@Data
public class InventoryResDto {

	private String skuCode;

	private Boolean inStock;

	private String status;

	private String availabilitystatus;

	private Integer stockQuantity;
}
