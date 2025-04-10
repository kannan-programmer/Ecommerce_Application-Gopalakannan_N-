package com.java.junit;

import com.java.dao.OrderProcessorRepositoryDaoImpl;
import com.java.exception.CustomerNotFoundException;
import com.java.model.Customer;
import com.java.model.OrderDetail;
import com.java.model.Product;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EcomOperationsTest {

    private static OrderProcessorRepositoryDaoImpl dao;

    private static Customer testCustomer;
    private static Product testProduct;

    @BeforeAll
    public static void setUp() throws Exception {
        dao = new OrderProcessorRepositoryDaoImpl();
        testCustomer = new Customer();
        testCustomer.setName("Gopala kannan");
        testCustomer.setEmail("kannan@example.com");
        testCustomer.setPassword("password123");
        boolean customerCreated = dao.createCustomer(testCustomer);
        assertTrue(customerCreated);
        testCustomer.setCustomerId(1);
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setPrice(49.99);
        testProduct.setDescription("Test Description");
        testProduct.setStockquantity(10);
        boolean productCreated = dao.createProduct(testProduct);
        assertTrue(productCreated);
        testProduct.setProductId(1);
    }

    @Test
    @Order(1)
    public void testCreateProductSuccessfully() throws Exception {
        Product product = new Product(0, "Laptop", 999.99, "Gaming Laptop", 5);
        boolean result = dao.createProduct(product);
        assertTrue(result);
    }

    @Test
    @Order(2)
    public void testAddProductToCartSuccessfully() throws Exception {
        boolean result = dao.addToCart(testCustomer, testProduct, 2);
        assertTrue(result);
    }

    @Test
    @Order(3)
    public void testPlaceOrderSuccessfully() throws Exception {
        Map<Product, Integer> map = new HashMap<>();
        map.put(testProduct, 1);
        List<Map<Product, Integer>> productList = new ArrayList<>();
        productList.add(map);

        boolean result = dao.placeOrder(testCustomer, productList, "123 Street, City");
        assertTrue(result);
    }

    @Test
    @Order(4)
    public void testGetOrdersForInvalidCustomer() throws ClassNotFoundException, CustomerNotFoundException {
        List<OrderDetail> orders = dao.getOrdersByCustomer(9999);
        assertTrue(orders.isEmpty(), "Expected no orders for invalid customer ID");
    }

    @Test
    @Order(5)
    public void testProductNotFoundException() {
        Product fakeProduct = new Product();
        fakeProduct.setProductId(9999); 
        Map<Product, Integer> productMap = new HashMap<>();
        productMap.put(fakeProduct, 1);

        List<Map<Product, Integer>> productList = new ArrayList<>();
        productList.add(productMap);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.placeOrder(testCustomer, productList, "Fake address");
        });

        assertTrue(exception.getMessage().contains("Product details not found"));
    }
}
