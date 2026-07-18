package com.deepak.productService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyProjectMacyProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectMacyProductServiceApplication.class, args);
	}

}
