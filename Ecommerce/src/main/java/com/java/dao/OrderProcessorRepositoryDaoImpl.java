package com.java.dao;
import java.util.List;
import java.util.Map;

import java.sql.*;
import java.util.*;

import com.java.exception.CustomerNotFoundException;
import com.java.model.Customer;
import com.java.model.OrderDetail;
import com.java.model.Product;
import com.java.util.ConnectionHelper;

public  class OrderProcessorRepositoryDaoImpl implements OrderProcessorRepositoryDao {
 
	
	
	    @Override
	    public boolean createProduct(Product product) throws ClassNotFoundException {
	        String cmd = "INSERT INTO products (Product_name, price, description, stock_quantity) VALUES (?, ?, ?,?)";
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {
	        	pst.setString(1, product.getName());
	        	pst.setDouble(2, product.getPrice());
	        	pst.setString(3, product.getDescription());
	        	pst.setInt(4, product.getStockquantity());
	            return pst.executeUpdate() > 0;
	        } catch (SQLException e) {
	            throw new RuntimeException("Error creating product: " + e.getMessage());
	        }
	    }

	    @Override
	    public boolean createCustomer(Customer customer) throws ClassNotFoundException {
	        String cmd = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {
	        	pst.setString(1, customer.getName());
	        	pst.setString(2, customer.getEmail().toLowerCase());
	        	pst.setString(3, customer.getPassword());
	            return pst.executeUpdate() > 0;
	        } catch (SQLException e) {
	            throw new RuntimeException("Error creating customer: " + e.getMessage());
	        }
	    }

	    @Override
	    public boolean deleteProduct(int productId) throws ClassNotFoundException {
	        String checkcmd = "SELECT COUNT(*) FROM cart WHERE product_id = ?";
	        String deletecmd = "DELETE FROM products WHERE product_id = ?";
	        
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(checkcmd)) {
	        	pst.setInt(1, productId);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                throw new RuntimeException("Cannot delete product: It exists in the cart.");
	            }
	            
	            try (PreparedStatement deletepst = con.prepareStatement(deletecmd)) {
	                deletepst.setInt(1, productId);
	                return deletepst.executeUpdate() > 0;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error deleting product: " + e.getMessage());
	        }
	    }

	    @Override
	    public boolean deleteCustomer(int customerId) throws ClassNotFoundException {
	        String checkcmd = "SELECT COUNT(*) FROM cart WHERE customer_id = ?";
	        String deletecmd = "DELETE FROM customers WHERE customer_id = ?";
	        
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(checkcmd)) {
	        	pst.setInt(1, customerId);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                throw new RuntimeException("Cannot delete customer: They have items in the cart.");
	            }
	            
	            try (PreparedStatement deletepst = con.prepareStatement(deletecmd)) {
	                deletepst.setInt(1, customerId);
	                return deletepst.executeUpdate() > 0;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error deleting customer: " + e.getMessage());
	        }
	    }


	    @Override
	    public boolean addToCart(Customer customer, Product product, int quantity) throws ClassNotFoundException {
	        String cmd = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {
	        	pst.setInt(1, customer.getCustomerId());
	        	pst.setInt(2, product.getProductId());
	        	pst.setInt(3, quantity);
	            return pst.executeUpdate() > 0;
	        } catch (SQLException e) {
	            throw new RuntimeException("Error adding to cart: " + e.getMessage());
	        }
	    }

	    @Override
	    public boolean removeFromCart(Customer customer, Product product) throws ClassNotFoundException {
	        String cmd = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {
	        	pst.setInt(1, customer.getCustomerId());
	        	pst.setInt(2, product.getProductId());
	            return pst.executeUpdate() > 0;
	        } catch (SQLException e) {
	            throw new RuntimeException("Error removing from cart: " + e.getMessage());
	        }
	    }
	    

	    @Override
	    public List<Product> getAllFromCart(Customer customer) throws ClassNotFoundException {
	        String cmd = "SELECT p.product_id, p.product_name, p.price, p.description, p.stock_quantity FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?";
	        List<Product> cartProducts = new ArrayList<>();
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {
	        	pst.setInt(1, customer.getCustomerId());
	            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	                Product product = new Product(
	                        rs.getInt("product_id"),
	                        rs.getString("product_name"),
	                        rs.getDouble("price"),
	                        rs.getString("description"),
	                        rs.getInt("stock_quantity")
	                );
	                cartProducts.add(product);
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error retrieving cart products: " + e.getMessage());
	        }
	        return cartProducts;
	    }

	    @Override
	    public boolean placeOrder(Customer customer, List<Map<Product, Integer>> productList, String shippingAddress) throws ClassNotFoundException {
	        String ordercmd = "INSERT INTO orders (customer_id, shipping_address, order_date, total_price) VALUES (?, ?, NOW(), ?)";
	        String orderItemcmd = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)"; 
	        String clearCartcmd = "DELETE FROM cart WHERE customer_id = ?";

	        try (Connection con = ConnectionHelper.getConnection()) {
	            con.setAutoCommit(false);
	            
	            double totalPrice = 0.0;
	            for (Map<Product, Integer> productMap : productList) {
	                for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
	                    Product product = getProductDetails(entry.getKey().getProductId());
	                    if (product == null) {
	                        throw new SQLException("Product details not found for ID: " + entry.getKey().getProductId());
	                    }
	                    totalPrice += product.getPrice() * entry.getValue();
	                }
	            }

	            PreparedStatement orderpst = con.prepareStatement(ordercmd, Statement.RETURN_GENERATED_KEYS);
	            orderpst.setInt(1, customer.getCustomerId());
	            orderpst.setString(2, shippingAddress);
	            orderpst.setDouble(3, totalPrice);
	            orderpst.executeUpdate();
	            
	            ResultSet rst = orderpst.getGeneratedKeys();
	            int orderId;
	            if (rst.next()) {
	                orderId = rst.getInt(1);
	            } else {
	                throw new SQLException("Failed to create order.");
	            }

	            try (PreparedStatement orderItemStmt = con.prepareStatement(orderItemcmd)) {
	                for (Map<Product, Integer> productMap : productList) {
	                    for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
	                        orderItemStmt.setInt(1, orderId);
	                        orderItemStmt.setInt(2, entry.getKey().getProductId());
	                        orderItemStmt.setInt(3, entry.getValue());
	                        orderItemStmt.addBatch(); 
	                    }
	                }
	                orderItemStmt.executeBatch(); 
	            }

	            try (PreparedStatement clearCartStmt = con.prepareStatement(clearCartcmd)) {
	                clearCartStmt.setInt(1, customer.getCustomerId());
	                clearCartStmt.executeUpdate();
	            }
	            

	            con.commit();
	            return true;
	        } catch (SQLException e) {
	            throw new RuntimeException("Error placing order: " + e.getMessage());
	        }
	    }
	    
	    public Product getProductDetails(int productId) throws SQLException, ClassNotFoundException {
	        String cmd = "SELECT product_id, product_name, price, description, stock_quantity FROM products WHERE product_id = ?";
	        
	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {

	        	pst.setInt(1, productId);
	            ResultSet rs = pst.executeQuery();

	            if (rs.next()) {
	                return new Product(
	                    rs.getInt("product_id"),
	                    rs.getString("product_name"),
	                    rs.getDouble("price"),
	                    rs.getString("description"),
	                    rs.getInt("stock_quantity")
	                );
	            }
	            return null;
	        }
	    }


	    @Override
	    public List<OrderDetail> getOrdersByCustomer(int customerId) throws ClassNotFoundException, CustomerNotFoundException {
	        String cmd = "SELECT oi.product_id, oi.quantity " +
	                     "FROM orders o " +
	                     "JOIN order_items oi ON o.order_id = oi.order_id " +
	                     "WHERE o.customer_id = ?";

	        List<OrderDetail> orderDetails = new ArrayList<>();

	        try (Connection con = ConnectionHelper.getConnection();
	             PreparedStatement pst = con.prepareStatement(cmd)) {

	        	pst.setInt(1, customerId);
	            ResultSet rs = pst.executeQuery();

	            while (rs.next()) {
	                int productId = rs.getInt("product_id");
	                int quantity = rs.getInt("quantity");

	                Product product = getProductDetails(productId);
	                if (product != null) {
	                    orderDetails.add(new OrderDetail(product, quantity));
	                }
	            }

	        } catch (SQLException e) {
	            throw new RuntimeException("Error retrieving order details: " + e.getMessage(), e);
	        }

	        return orderDetails;
	    }

	    
	    
	}


