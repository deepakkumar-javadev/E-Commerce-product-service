package com.deepak.productService.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				// =========================
				// CSRF
				// =========================
				.csrf(csrf -> csrf.disable())

				// =========================
				// SESSION
				// =========================
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// =========================
				// AUTHORIZATION
				// =========================
				.authorizeHttpRequests(auth -> auth

						// =========================
						// ADMIN APIs
						// =========================
						.requestMatchers("/product/create", "/product/update/**", "/product/updateprice/**",
								"/product/delete/**")
						.hasRole("ADMIN")

						// =========================
						// CUSTOMER + ADMIN SEARCH APIs
						// =========================
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
						// =========================
						// CUSTOMER + ADMIN SEARCH APIs
						// =========================
						.requestMatchers("/product/searchproducts", "/product/searchByName", "/product/filter/**",
								"/product/getproduct/**", "/product/get/**")
						.hasAnyRole("CUSTOMER", "ADMIN")

						// =========================
						// OTHER PRODUCT APIs
						// =========================
						.requestMatchers("/product/**").hasAnyRole("CUSTOMER", "ADMIN")

						// =========================
						// EVERYTHING ELSE
						// =========================
						.anyRequest().authenticated())

				// =========================
				// JWT FILTER
				// =========================
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}