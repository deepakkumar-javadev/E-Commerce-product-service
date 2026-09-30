package com.deepak.productService.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.deepak.productService.Config.FeignConfig;
import com.deepak.productService.DTO.InventoryRequest;
import com.deepak.productService.DTO.InventoryResDto;

@FeignClient(name = "ECOM-INVENTORY-SERVICE", url = "http://localhost:8084",configuration = FeignConfig.class)
public interface InventoryClient {

	@PostMapping("/stock/create")
	public void createInventory(@RequestBody InventoryRequest request);

	// getInventories
	@PostMapping("/stock/getInventories")
	List<InventoryResDto> getInventories(@RequestBody List<String> skuCodes);

	@GetMapping("/stock/getstock/{skuCode}")
	public InventoryResDto getInventorystock(@PathVariable String skuCode);
    
}
