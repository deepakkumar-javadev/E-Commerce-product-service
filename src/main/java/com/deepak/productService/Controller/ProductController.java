package com.deepak.productService.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.productService.DTO.productRequest;
import com.deepak.productService.DTO.productResponse;
import com.deepak.productService.DTO.productResponseUser;
import com.deepak.productService.Service.productService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // create automatically contructor for this class and pass arg which is "Final"
@RequestMapping("/product")
public class ProductController {

	private final productService service; // final
	
	// 1. create product
	@PostMapping("/create")
	public String createProduct(@RequestBody productRequest req) {
		return service.createProduct(req);
	}

	//2. show all products
	@GetMapping("/searchproducts")
	public List<productResponse> getProduct() {
		return service.getAllproduct();
	}

	//3. show single products using skucode
	@GetMapping("/getproduct/{skucode}")
	public productResponseUser getOneProduct(@PathVariable String skucode) {
		return service.fetchproduct(skucode);
	}
	
	//4.show productBYid   & addToCart
	@GetMapping("/get/{productId}")
	public productResponse getProductById(@PathVariable Long productId) {
		return service.getProductById(productId);
	}

	// update product
	@PutMapping("/update/{Id}")
	public productResponse UpdateProduct(@PathVariable Long productId, @RequestBody productRequest req) {
		return service.updateProduct(productId, req);
	}

	// delete product
	@DeleteMapping("delete/{Id}")
	public String DeleteProduct(@PathVariable Long productId) {
		return service.deleteProduct(productId);
	}

	// search product by name
	@GetMapping("/searchByName") // query paramter
	public productResponse SearchProductByName(@RequestParam String name) {

		return service.getProductByName(name);
	}
	
	
	@GetMapping("/filter/category")
	public List<productResponse> filterByCategory(@RequestParam String category) {
	    return service.filterByCategory(category);
	}

	@GetMapping("/filter/price")
	public List<productResponse> filterByCategory(@RequestParam double price) {
	    return service.filterByPrice(price);
	}

	
}
