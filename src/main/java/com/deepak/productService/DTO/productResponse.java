package com.deepak.productService.DTO;

import lombok.Data;

// user output DTO [user response data binding class]
// after processing the request data will bind with this class and then data send to the user....
@Data
public class productResponse {

	private Long productid;
    private String name;
    private double price;
    private String brand;
    private String color;
    private String size;
    private String category;
    private String availabilitystatus;
	private int stockQuantity;
	private String discription;

}
