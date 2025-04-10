package com.java.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.java.model.Order_Item;

public class Order_ItemTest {

    @Test
    public void testNoArgsConstructor() {
        Order_Item item = new Order_Item();
        assertNotNull(item);
    }

    @Test
    public void testAllArgsConstructor() {
        Order_Item item = new Order_Item(2, 1002, 502, 5, 499.50);

        assertEquals(2, item.getOrderItemId());
        assertEquals(1002, item.getOrderId());
        assertEquals(502, item.getProductId());
        assertEquals(5, item.getQuantity());
        assertEquals(499.50, item.getPrice());
    }

    @Test
    public void testSetters() {
        Order_Item item = new Order_Item();
        item.setOrderItemId(10);
        item.setOrderId(2000);
        item.setProductId(3000);
        item.setQuantity(4);
        item.setPrice(123.45);

    }

    @Test
    public void testGetters() {
        Order_Item item = new Order_Item();
        item.setOrderItemId(10);
        item.setOrderId(2000);
        item.setProductId(3000);
        item.setQuantity(4);
        item.setPrice(123.45);

        assertEquals(10, item.getOrderItemId());
        assertEquals(2000, item.getOrderId());
        assertEquals(3000, item.getProductId());
        assertEquals(4, item.getQuantity());
        assertEquals(123.45, item.getPrice());
    }

    @Test
    public void testToString() {
        Order_Item item = new Order_Item(3, 1003, 503, 2, 150.0);
        String str = item.toString();

        assertTrue(str.contains("orderItemId=3"));
        assertTrue(str.contains("orderId=1003"));
        assertTrue(str.contains("productId=503"));
        assertTrue(str.contains("quantity=2"));
        assertTrue(str.contains("price=150.0"));
    }
}
