package com.java.dao;

import com.java.exception.CustomerNotFoundException;
import com.java.exception.ProductNotFoundException;
import com.java.model.Customer;
import com.java.model.OrderDetail;
import com.java.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderProcessorRepositoryDao {
    
    boolean createProduct(Product product) throws ClassNotFoundException;
    boolean createCustomer(Customer customer) throws ClassNotFoundException;
    boolean deleteProduct(int productId) throws ClassNotFoundException, ProductNotFoundException;
    boolean deleteCustomer(int customerId) throws ClassNotFoundException, CustomerNotFoundException;
	boolean addToCart(Customer customer, Product product, int quantity) throws ClassNotFoundException;
	boolean removeFromCart(Customer customer, Product product) throws ClassNotFoundException;
    List<Product> getAllFromCart(Customer customer) throws ClassNotFoundException;
    public Product getProductDetails(int productId) throws SQLException, ClassNotFoundException;
    boolean placeOrder(Customer customer, List<Map<Product, Integer>> productList, String shippingAddress) throws ClassNotFoundException;
    public List<OrderDetail> getOrdersByCustomer(int customerId) throws ClassNotFoundException, CustomerNotFoundException;
	



}