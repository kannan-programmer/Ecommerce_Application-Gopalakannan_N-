package com.java.junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.java.model.Product;

public class ProductTest {

    @Test
    public void testNoArgsConstructor() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product(2, "Phone", 499.49, "Smartphone", 100);

        assertEquals(2, product.getProductId());
        assertEquals("Phone", product.getName());
        assertEquals(499.49, product.getPrice());
        assertEquals("Smartphone", product.getDescription());
        assertEquals(100, product.getStockquantity());
    }

    @Test
    public void testSetters() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Laptop");
        product.setPrice(899.99);
        product.setDescription("Gaming Laptop");
        product.setStockquantity(50);

    }

    @Test
    public void testGetters() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Laptop");
        product.setPrice(899.99);
        product.setDescription("Gaming Laptop");
        product.setStockquantity(50);

        assertEquals(1, product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals(899.99, product.getPrice());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(50, product.getStockquantity());
    }

    @Test
    public void testToString() {
        Product product = new Product(3, "Tablet", 299.99, "Android tablet", 20);
        String expected = "Product(productId=3, name=Tablet, price=299.99, description=Android tablet, Stockquantity=20)";
        assertEquals(expected, product.toString());
    }
}
