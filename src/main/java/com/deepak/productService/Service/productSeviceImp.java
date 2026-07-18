package com.deepak.productService.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.deepak.productService.Client.InventoryClient;
import com.deepak.productService.DTO.InventoryRequest;
import com.deepak.productService.DTO.InventoryResDto;
import com.deepak.productService.DTO.productRequest;
import com.deepak.productService.DTO.productResponse;
import com.deepak.productService.DTO.productResponseUser;
import com.deepak.productService.Repository.productRepository;
import com.deepak.productService.entity.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class productSeviceImp implements productService {

	private final productRepository repo; // dependency provided throgh costructor injection ...
	private final InventoryClient invClient;

	// ***** use lombok annotation which become constructor automatically
//	public productSeviceImp(productRepository repo,InventoryClient invClient) {
//		super();
//		this.repo = repo;
//		this.invClient=invClient;
//	}

	// Logic to Add products
	@Override
	public String createProduct(productRequest req) {

		// dto data bind with entity to store in db

		// getsku code
		String sku = generateSkuCode(req);

		// create entity object
		product p = new product();
		p.setName(req.getName());
		p.setPrice(req.getPrice());
		p.setSize(req.getSize());
		p.setBrand(req.getBrand());
		p.setCategory(req.getCategory());
		p.setColor(req.getColor());
		p.setRating(5);
		p.setCreatedAt(LocalDateTime.now());

		// save product | again to get the generated product id from product table to
		// that i can add Pid in Sku for unique sku
		p.setSkuCode(sku);

		repo.save(p);

		// send data to inventory
		InventoryRequest invReqDto = new InventoryRequest();

		invReqDto.setSkuCode(p.getSkuCode()); // comming from product db..
		invReqDto.setStockQuantity(req.getStockQuantity());// comming from productRequest

		invClient.createInventory(invReqDto);

		return "Product Added Successfully.......";
	}

	// Logic to getALL products
	@Override
	public List<productResponse> getAllproduct() {
		// get all productList from productEntity
		List<product> allproduct = repo.findAll();
					if(allproduct == null) {
						throw new RuntimeException("No product exists...");
					}
		
		// 2. Sabhi SKU Codes nikalo
		List<String> skuCodes = allproduct.stream().map(product::getSkuCode).toList();

		// 3. Inventory Service ko ek hi API call   | getting all inventory table data in List
		List<InventoryResDto> inventories = invClient.getInventories(skuCodes);

		// 4. converting data in key : value  | key= skucode  : value= InventoryResDto object ...
		Map<String, InventoryResDto> inventoryMap = inventories.stream()
				.collect(Collectors.toMap(InventoryResDto::getSkuCode, // skucode
						i -> i // inventoryResDto object
				));

		//5 bind inventoryDtoRes to productDtoRes
		
		
		List<productResponse> list = allproduct.stream().map(p -> {
			// get product data from entity & set ProductResposne 
			productResponse res = new productResponse();

			res.setProductid(p.getProductId());
			res.setName(p.getName());
			res.setPrice(p.getPrice());
			res.setBrand(p.getBrand());
			res.setCategory(p.getCategory());
			res.setColor(p.getColor());
			res.setSize(p.getSize());
			res.setDiscription(p.getDescription());
			// show some inventory data for each product

			InventoryResDto inventoryresDto = inventoryMap.get(p.getSkuCode());

			if (inventoryresDto != null) {

				res.setStockQuantity(inventoryresDto.getStockQuantity());
				res.setAvailabilitystatus(inventoryresDto.getStockQuantity() > 0 ? "In_Stock" : "Out_Of_Stock");
				
			}

			// return ProductResponse
			return res;

		}).toList();

		return list;
	}

	// Logic to get Product by Id.
	@Override
	public productResponse getProductById(Long productid) {
		// get product in entity form
		product p = repo.getProductByProductId(productid);
			if(p==null) {
				throw new RuntimeException("product not found with id : " + productid);
			}
		
		// for acces invClient data we need objo of client

		InventoryResDto inventoryresDto = invClient.getInventorystock(p.getSkuCode());

		// convert data in entity to DTO

		productResponse res = new productResponse();
		res.setProductid(p.getProductId());
		res.setName(p.getName());
		res.setBrand(p.getBrand());
		res.setPrice(p.getPrice());
		res.setCategory(p.getCategory());
		res.setAvailabilitystatus(inventoryresDto.getAvailabilitystatus());

		return res;
	}

	// Logic TO updateProduct
	@Override
	public productResponse updateProduct(Long productid, productRequest req) {
		// 1 get perticuler product where you want to make some changes;
		product product = repo.getProductByProductId(productid);
		if(product==null) {
			throw new RuntimeException("product not found with id : " + productid);
		}
		// 2 update data in exiting data
		product.setName(req.getName());
		product.setPrice(req.getPrice());
		product.setBrand(req.getBrand());
		product.setCategory(req.getCategory());
		// product.setStockQuantity(req.getStockQuantity());

		// 3 save updated data in repository...
		product updatedProduct = repo.save(product);

		// 4 set response
		// get UPDATED DATA FROM UPDATED REPO & convert in DTO RESPONSE

		productResponse res = new productResponse();
		res.setProductid(updatedProduct.getProductId());
		res.setName(updatedProduct.getName());
		res.setBrand(updatedProduct.getBrand());
		res.setPrice(updatedProduct.getPrice());
		res.setCategory(updatedProduct.getCategory());

		return res;
	}

	// Logic to Delete product
	@Override
	public String deleteProduct(Long productid) {
		// get product details from repo
		product product = repo.getProductByProductId(productid);
		if(product==null) {
			throw new RuntimeException("product not found with id : " + productid);
		}
		// pass product to delete
		repo.delete(product);

		return "product deleted successfully..";
	}

	// logic for getproductByName
	@Override
	public productResponse getProductByName(String name) {

		product p = repo.findByName(name);

		if (p == null) {
			throw new RuntimeException("Product not found with name : " + name);
		}

		// if product found in repo

		productResponse resp = new productResponse();
		resp.setName(p.getName());
		resp.setBrand(p.getBrand());
		resp.setCategory(p.getCategory());
		resp.setPrice(p.getPrice());

		return resp;
	}

	// logic to filter product by category..
	@Override
	public List<productResponse> filterByCategory(String category) {

		List<product> productEntity = repo.findByCategory(category);
		if (productEntity.isEmpty()) {
			throw new RuntimeException("product not found exception " + category);
		}

		// if product found in repo
		List<productResponse> responseList = new ArrayList<>();

		for (product p : productEntity) {
			productResponse resp = new productResponse();
			resp.setName(p.getName());
			resp.setBrand(p.getBrand());
			resp.setCategory(p.getCategory());
			resp.setPrice(p.getPrice());

			responseList.add(resp);
		}
		return responseList;
	}

	// Logic to filtere product by price

	@Override
	public List<productResponse> filterByPrice(double price) {

		List<product> productEntity = repo.findByPrice(price);
		if (productEntity.isEmpty()) {
			throw new RuntimeException("product not found of this  " + price);
		}

		// if product found in repo
		List<productResponse> responseList = new ArrayList<>();

		for (product p : productEntity) {
			productResponse resp = new productResponse();
			resp.setName(p.getName());
			resp.setBrand(p.getBrand());
			resp.setCategory(p.getCategory());
			resp.setPrice(p.getPrice());

			responseList.add(resp);
		}
		return responseList;
	}

	// SKU Generate code ....

	public String generateSkuCode(productRequest req) {
		// to generate unic id
		String unicId = UUID.randomUUID().toString().substring(0, 6);

		String sku = req.getBrand().toUpperCase().charAt(0) + "-" + req.getName().toUpperCase().charAt(0) + "-"
				+ req.getColor().toUpperCase().charAt(0) + "-" + req.getSize().charAt(0) + "-" + unicId; // unic id of 8
																											// letter

		return sku;
	}

	@Override
	public productResponseUser fetchproduct(String skucode) {
						
		product p1 = repo.findBySkuCode(skucode).orElseThrow(() -> new RuntimeException("product not found....") );
		InventoryResDto invResDto= invClient.getInventorystock(skucode);
		if(invResDto ==null) {
			throw new RuntimeException("inventory not found ");
		}
		productResponseUser proRes = new productResponseUser();
		proRes.setName(p1.getName());
		proRes.setBrand(p1.getBrand());
		proRes.setColor(p1.getColor());
		proRes.setPrice(p1.getPrice());
		proRes.setCategory(p1.getCategory());
		proRes.setSize(p1.getSize());
		proRes.setStockQuantity(invResDto.getStockQuantity());
		proRes.setAvailabilitystatus(invResDto.getStockQuantity() > 0 ? "In_Stock":"Out_Of_Stock");
		
		return proRes ;
	}

}
