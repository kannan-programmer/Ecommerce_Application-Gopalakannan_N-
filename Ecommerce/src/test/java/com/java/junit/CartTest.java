package com.java.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.java.model.Cart;
import com.java.model.Product;

public class CartTest {

    @Test
    public void testNoArgsConstructor() {
        Cart cart = new Cart();
        assertNotNull(cart);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product(2, "Pencil", 5.0, "HB Pencil", 300);
        List<Product> products = List.of(product);

        Cart cart = new Cart(101, 201, products);

        assertEquals(101, cart.getCartId());
        assertEquals(201, cart.getCustomerId());
        assertEquals(products, cart.getProducts());
    }

    // Test Setters
    @Test
    public void testSetters() {
        Cart cart = new Cart();

        cart.setCartId(100);
        cart.setCustomerId(200);

        Product product = new Product(1, "Pen", 10.0, "Blue ink pen", 100);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        cart.setProducts(productList);

    }

    @Test
    public void testGetters() {
        Product product = new Product(1, "Pen", 10.0, "Blue ink pen", 100);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Cart cart = new Cart();
        cart.setCartId(100);
        cart.setCustomerId(200);
        cart.setProducts(productList);

        assertEquals(100, cart.getCartId());
        assertEquals(200, cart.getCustomerId());
        assertEquals(1, cart.getProducts().size());
        assertEquals("Pen", cart.getProducts().get(0).getName());
    }

    @Test
    public void testToStringContainsValues() {
        Product product = new Product(3, "Notebook", 50.0, "A4 size", 200);
        List<Product> products = List.of(product);

        Cart cart = new Cart(102, 202, products);
        String cartString = cart.toString();

        assertTrue(cartString.contains("cartId=102"));
        assertTrue(cartString.contains("customerId=202"));
        assertTrue(cartString.contains("Notebook"));
    }

    @Test
    public void testEmptyProductList() {
        Cart cart = new Cart(103, 203, new ArrayList<>());
        assertEquals(103, cart.getCartId());
        assertEquals(203, cart.getCustomerId());
        assertTrue(cart.getProducts().isEmpty());
    }

    @Test
    public void testNullProducts() {
        Cart cart = new Cart();
        cart.setProducts(null);
        assertNull(cart.getProducts());
    }

    @Test
    public void testIndividualProductDetails() {
        Product product = new Product(4, "Eraser", 2.0, "Soft white eraser", 80);
        List<Product> products = new ArrayList<>();
        products.add(product);

        Cart cart = new Cart();
        cart.setCartId(104);
        cart.setCustomerId(204);
        cart.setProducts(products);

        Product resultProduct = cart.getProducts().get(0);
        assertEquals(4, resultProduct.getProductId());
        assertEquals("Eraser", resultProduct.getName());
        assertEquals(2.0, resultProduct.getPrice());
        assertEquals("Soft white eraser", resultProduct.getDescription());
        assertEquals(80, resultProduct.getStockquantity());
    }
}
