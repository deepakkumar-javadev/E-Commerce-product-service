# E-Commerce Product Service

Product management microservice for an e-commerce application built using **Java, Spring Boot and MySQL**.

This service manages product information and communicates with the **Inventory Service** using OpenFeign.

## Features

* Product creation and management
* Product search
* Product update
* Product deletion
* Product details retrieval
* Inventory integration using OpenFeign
* JWT-based authentication
* Role-based authorization
* REST APIs
* MySQL database integration
* Spring Data JPA
* Spring Boot Actuator

## APIs

| API                                  | Description       |
| ------------------------------------ | ----------------- |
| `POST /product/addproduct`           | Add a new product |
| `GET /product/getproduct/{id}`       | Get product by ID |
| `GET /product/searchproducts`        | Search products   |
| `PUT /product/updateproduct/{id}`    | Update product    |
| `DELETE /product/deleteproduct/{id}` | Delete product    |

> API paths may vary depending on the current controller configuration.

## Service Communication

The Product Service communicates with the Inventory Service using **OpenFeign**.

```text
Product Service :8083
        |
        | OpenFeign
        ↓
Inventory Service :8084
```

Product-related inventory operations are handled by the Inventory Service.

## Technologies

* Java 17
* Spring Boot 3.5.6
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* OpenFeign
* Eureka Service Discovery
* Maven
* Spring Boot Actuator

## Configuration

Main configuration file:

```text
src/main/resources/application.yml
```

Default service port:

```text
8083
```

Local URL:

```text
http://localhost:8083
```

## Running the Service

Make sure MySQL and the required dependent services are running.

Run using Maven:

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

## Project Role

The Product Service is part of the E-Commerce Microservices application and is responsible for managing product-related operations.

It works with the Inventory Service to maintain product stock information.


