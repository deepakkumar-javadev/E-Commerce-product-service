package com.deepak.productService.Service;

import java.util.List;

import com.deepak.productService.DTO.productRequest;
import com.deepak.productService.DTO.productResponse;
import com.deepak.productService.DTO.productResponseUser;

public interface productService {

	public String createProduct(productRequest req);

	public List<productResponse> getAllproduct();

	public productResponseUser fetchproduct(String skucode);

	public productResponse getProductById(Long productid);

	public productResponse updateProduct(Long productid, productRequest req);

	public String deleteProduct(Long productid);

	public productResponse getProductByName(String name);

	public List<productResponse> filterByCategory(String category);

	public List<productResponse> filterByPrice(double price);

	public String generateSkuCode(productRequest req);

}
