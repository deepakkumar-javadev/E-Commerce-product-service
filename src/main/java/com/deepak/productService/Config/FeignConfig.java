package com.deepak.productService.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {

			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();

			System.out.println("========== FEIGN DEBUG ==========");

			System.out.println("RequestContextAttributes: " + (attributes != null));

			if (attributes != null) {

				HttpServletRequest request = attributes.getRequest();

				String authorization = request.getHeader("Authorization");

				System.out.println("Authorization present: " + (authorization != null));

				if (authorization != null) {

					requestTemplate.header("Authorization", authorization);
				}
			}

			System.out.println("Feign URL: " + requestTemplate.url());

			System.out.println("================================");
		};
	}
}