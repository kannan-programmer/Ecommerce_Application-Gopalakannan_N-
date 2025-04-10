package com.java.junit;

import com.java.dao.OrderProcessorRepositoryDao;


import com.java.dao.OrderProcessorRepositoryDaoImpl;
import com.java.exception.CustomerNotFoundException;
import com.java.exception.OrderNotFoundException;
import com.java.exception.ProductNotFoundException;
import com.java.model.Customer;
import com.java.model.OrderDetail;
import com.java.model.Product;
import com.java.util.ConnectionHelper;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderProcessRepositoryTest {

    private static OrderProcessorRepositoryDao dao;
    private static Customer testCustomer;
    private static Product testProduct;

    @BeforeAll
    public static void setup() throws ClassNotFoundException {
        dao = new OrderProcessorRepositoryDaoImpl();
    }

    @Test
    @Order(1)
    public void createCustomerTest() throws Exception {
        testCustomer = new Customer();
        testCustomer.setName("Test User");
        testCustomer.setEmail("testuser@example.com");
        testCustomer.setPassword("password123");

        boolean isCustomerCreated = dao.createCustomer(testCustomer);
        assertTrue(isCustomerCreated, "Customer should be created successfully");

        testCustomer.setCustomerId(1); 
    }

    @Test
    @Order(2)
    public void createProductTest() throws Exception {
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        testProduct.setDescription("This is a test product");
        testProduct.setStockquantity(10);

        boolean isProductCreated = dao.createProduct(testProduct);
        assertTrue(isProductCreated, "Product should be created successfully");

        testProduct.setProductId(1); 
    }

    @Test
    @Order(3)
    public void testAddToCart() throws Exception {
        boolean result = dao.addToCart(testCustomer, testProduct, 2);
        assertTrue(result, "Product should be added to cart");
    }

    @Test
    @Order(4)
    public void testGetAllFromCart() throws Exception {
        dao.addToCart(testCustomer, testProduct, 1);
        List<Product> cartProducts = dao.getAllFromCart(testCustomer);
        assertNotNull(cartProducts, "Cart should not be null");
        assertFalse(cartProducts.isEmpty(), "Cart should not be empty");

        boolean productFound = cartProducts.stream()
                .anyMatch(p -> p.getProductId() == testProduct.getProductId());
        assertTrue(productFound, "Product should be in the cart");
    }

    @Test
    @Order(5)
    public void testPlaceOrder() throws Exception {
        List<Map<Product, Integer>> productList = new ArrayList<>();
        Map<Product, Integer> productMap = new HashMap<>();
        productMap.put(testProduct, 2);
        productList.add(productMap);

        boolean result = dao.placeOrder(testCustomer, productList, "Test Address");
        assertTrue(result, "Order should be placed successfully");
    }

    @Test
    @Order(6)
    public void testGetOrdersByCustomer() throws Exception {
        List<OrderDetail> orders = dao.getOrdersByCustomer(testCustomer.getCustomerId());
        assertNotNull(orders, "Order list should not be null");
        assertFalse(orders.isEmpty(), "Order list should not be empty");
        
    }

    
    @Test
    @Order(7)
    public void testRemoveFromCart() throws Exception {
        dao.addToCart(testCustomer, testProduct, 1);
        boolean removed = dao.removeFromCart(testCustomer, testProduct);
        assertTrue(removed, "Product should be removed from cart");

        List<Product> cartProducts = dao.getAllFromCart(testCustomer);
        boolean stillInCart = cartProducts.stream()
                .anyMatch(p -> p.getProductId() == testProduct.getProductId());
        assertFalse(stillInCart, "Product should not be in the cart");
    }

    @Test
    @Order(8)
    public void testDeleteProduct() throws Exception {
        int productId;
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO products (product_name, price, description, stock_quantity) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, "Test Product Delete");
            pst.setDouble(2, 99.99);
            pst.setString(3, "For Deletion");
            pst.setInt(4, 10);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            assertTrue(rs.next());
            productId = rs.getInt(1);
        }

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement pst = con.prepareStatement("DELETE FROM cart WHERE product_id = ?")) {
            pst.setInt(1, productId);
            pst.executeUpdate();
        }

        boolean result = dao.deleteProduct(productId);
        assertTrue(result, "Product should be deleted");
    }

    @Test
    @Order(9)
    public void testDeleteCustomer() throws Exception {
        int customerId;
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, "Delete Me");
            pst.setString(2, "deleteme@example.com");
            pst.setString(3, "pass");
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            assertTrue(rs.next());
            customerId = rs.getInt(1);
        }

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement pst = con.prepareStatement("DELETE FROM cart WHERE customer_id = ?")) {
            pst.setInt(1, customerId);
            pst.executeUpdate();
        }

        boolean result = dao.deleteCustomer(customerId);
        assertTrue(result, "Customer should be deleted");
    }

    @Test
    @Order(10)
    public void placeOrderWithInvalidProductThrowsExceptionTest() throws Exception {
        Product invalidProduct = new Product();
        invalidProduct.setProductId(-1); // Invalid ID

        Map<Product, Integer> productMap = new HashMap<>();
        productMap.put(invalidProduct, 1);
        List<Map<Product, Integer>> productList = List.of(productMap);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.placeOrder(testCustomer, productList, "Invalid Test Address");
        });

        assertTrue(exception.getMessage().contains("Product details not found"));
    }
    

    @Test
    @Order(11)
    public void testGetNonExistentProductThrowsException() {
        assertThrows(ProductNotFoundException.class, () -> {
            Product product = dao.getProductDetails(8888);
            if (product == null) {
                throw new ProductNotFoundException("Product not found");
            }
        });
    }

    

    @Test
    @Order(12)
    public void testGetOrdersByNonExistentCustomerThrowsException() {
        assertThrows(OrderNotFoundException.class, () -> {
            List<OrderDetail> orders = dao.getOrdersByCustomer(7777);
            if (orders == null || orders.isEmpty()) {
                throw new OrderNotFoundException("No orders found for customer");
            }
            
        });
    }
    

    @Test
    @Order(13)
    public void testGetOrdersByCustomerThrowsCustomerNotFoundException() {
        dao = new OrderProcessorRepositoryDaoImpl() {
            @Override
            public List<OrderDetail> getOrdersByCustomer(int customerId) throws CustomerNotFoundException {
                throw new CustomerNotFoundException("Customer not found for ID: " + customerId);
            }
        };

        assertThrows(CustomerNotFoundException.class, () -> {
            dao.getOrdersByCustomer(testCustomer.getCustomerId());
        });
    }
  


  
    
}
