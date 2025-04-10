package com.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order_Item {
	
	private int orderItemId;
    private int orderId;
    private  int productId;
    private int quantity;
    private double price;
	
    
}
