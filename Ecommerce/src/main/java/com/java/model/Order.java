package com.java.model;

import java.util.Date;
import java.util.List;

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
public class Order {
	private int orderId;
    private int customerId;
    private List<Product> products;
    private double totalPrice;
    private Date orderDate;
}
