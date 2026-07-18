package com.deepak.productService.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.deepak.productService.entity.product;

@Repository
public interface productRepository extends JpaRepository<product,Integer>{

	// for practice  Alternative  method findById();
	public product  getProductByProductId(Long productid);
	
	public product findByName(String name);
	
	public List<product> findByCategory(String category);
	
	public List<product> findByPrice(double price);
	
	public Optional<product> findBySkuCode(String skuCode);
	
}
