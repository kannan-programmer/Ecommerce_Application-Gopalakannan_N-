package com.java.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.java.model.Order;
import com.java.model.Product;

public class OrderTest {

    private List<Product> products;
    private Date orderDate;

    @BeforeEach
    public void setUp() {
        Product p1 = new Product(1, "Laptop", 1000.0, "Gaming laptop", 10);
        Product p2 = new Product(2, "Mouse", 50.0, "Wireless mouse", 20);
        products = Arrays.asList(p1, p2);
        orderDate = new Date();
    }

    @Test
    public void testNoArgsConstructor() {
        Order emptyOrder = new Order();
        assertNotNull(emptyOrder);
    }

    @Test
    public void testAllArgsConstructor() {
        Order order = new Order(101, 1, products, 1050.0, orderDate);

        assertEquals(101, order.getOrderId());
        assertEquals(1, order.getCustomerId());
        assertEquals(products, order.getProducts());
        assertEquals(1050.0, order.getTotalPrice());
        assertEquals(orderDate, order.getOrderDate());
    }

    @Test
    public void testSetters() {
        Order order = new Order();

        order.setOrderId(202);
        order.setCustomerId(3);
        order.setProducts(products);
        order.setTotalPrice(1250.0);
        order.setOrderDate(orderDate);

    }

    @Test
    public void testGetters() {
        Order order = new Order();

        order.setOrderId(202);
        order.setCustomerId(3);
        order.setProducts(products);
        order.setTotalPrice(1250.0);
        order.setOrderDate(orderDate);

        assertEquals(202, order.getOrderId());
        assertEquals(3, order.getCustomerId());
        assertEquals(products, order.getProducts());
        assertEquals(1250.0, order.getTotalPrice());
        assertEquals(orderDate, order.getOrderDate());
    }

    @Test
    public void testToString() {
        Order order = new Order(101, 1, products, 1050.0, orderDate);
        String result = order.toString();

        assertTrue(result.contains("orderId=101"));
        assertTrue(result.contains("customerId=1"));
        assertTrue(result.contains("totalPrice=1050.0"));
        assertTrue(result.contains("orderDate="));
    }
}
