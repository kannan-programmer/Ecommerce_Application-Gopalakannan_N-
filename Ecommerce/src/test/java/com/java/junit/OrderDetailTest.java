package com.java.junit;

import com.java.model.OrderDetail;
import com.java.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailTest {

    @Test
    public void testNoArgsConstructor() {
        OrderDetail orderDetail = new OrderDetail();
        assertNotNull(orderDetail);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product(1, "Monitor", 150.0, "24-inch FHD monitor", 20);
        OrderDetail orderDetail = new OrderDetail(product, 3);

        assertEquals(1, orderDetail.getProduct().getProductId());
        assertEquals("Monitor", orderDetail.getProduct().getName());
        assertEquals(3, orderDetail.getQuantity());
    }

    @Test
    public void testSetters() {
        Product product = new Product();
        product.setProductId(5);
        product.setName("Headphones");
        product.setPrice(99.99);
        product.setDescription("Noise-canceling headphones");
        product.setStockquantity(30);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(4);

    }

    @Test
    public void testGetters() {
        Product product = new Product(2, "Keyboard", 75.0, "Mechanical RGB keyboard", 100);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(10);

        assertEquals("Keyboard", orderDetail.getProduct().getName());
        assertEquals(10, orderDetail.getQuantity());
        assertEquals(2, orderDetail.getProduct().getProductId());
    }

    @Test
    public void testToString() {
        Product product = new Product(2, "Keyboard", 75.0, "Mechanical RGB keyboard", 100);
        OrderDetail orderDetail = new OrderDetail(product, 10);

        String result = orderDetail.toString();

        assertTrue(result.contains("Keyboard"));
        assertTrue(result.contains("10"));
    }
}
