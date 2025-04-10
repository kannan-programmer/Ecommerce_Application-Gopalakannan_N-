package com.java.main;

import com.java.dao.*;
import com.java.exception.CustomerNotFoundException;
import com.java.exception.ProductNotFoundException;
import com.java.model.*;

import java.util.*;

public class EcomApp {
    private static final Scanner scanner = new Scanner(System.in);
    
  
    static OrderProcessorRepositoryDao orderRepo;
	static Scanner sc;
	
	
	static {
		orderRepo =  new OrderProcessorRepositoryDaoImpl();
		sc = new Scanner(System.in);
	}
    public static void main(String[] args) throws CustomerNotFoundException, ProductNotFoundException {
    	 int choice ;
        do {
        	System.out.println("== Welcome to E-Commerce-Application ==");
            System.out.println("----------------------------------------");
            System.out.println("Select your option from below");
            System.out.println("1. Register Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Place Order");
            System.out.println("7. View Customer Order");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

              choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                
                case 1 :
				try {
					createCustomer();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                	break;
                case 2 :
    				try {
    					createProduct();
    				} catch (ClassNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                    	break;
                    case 3 :
    				try {
    					deleteProduct();
    				} catch (ClassNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                    	break;
                
                case 4 :
				try {
					addToCart();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                	break;
                
                case 5 :
				try {
					viewCart();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                	break;
                case 6 :
				try {
					placeOrder();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                	break;
                case 7 :
				try {
					viewOrders();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                	break;
                case 8 :
                {
                    System.out.println("Exiting system....  \nVisit us Again Thank you!");
                    System.exit(0);
                }
                
            }
        } while(choice != 8);
    }
        

	private static void createProduct() throws ClassNotFoundException {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product Stackquantity: ");
        int Stackquantity = scanner.nextInt();

        Product product = new Product(0, name, price, description,Stackquantity);
        boolean success = orderRepo.createProduct(product);
        System.out.println(success ? "Product created successfully!\n" : "*** Failed to create product. ***");
    }

	private static void deleteProduct() throws ClassNotFoundException, ProductNotFoundException {
	    System.out.print("Enter product ID to delete: ");
	    int productId = scanner.nextInt();
	    
	    try {
	        boolean success = orderRepo.deleteProduct(productId);
	        System.out.println(success ? "Product deleted successfully....!\n" : "*** Product not found or could not be deleted. ***");
	    } catch (RuntimeException e) {
	        System.err.println("Error: " + e.getMessage());
	    }
	}


	private static void createCustomer() throws ClassNotFoundException, CustomerNotFoundException {
	    System.out.println("Select From The Options");
	    System.out.println("-> 1.Add New Customer");
	    System.out.println("-> 2.Delete The Existing Customer");
	    System.out.print("Enter Your Option: ");
	    int op = scanner.nextInt();
	    scanner.nextLine();
	    switch(op) {
	        case 1:
	            try {
	                System.out.print("Enter customer name: ");
	                String name = scanner.nextLine();
	                System.out.print("Enter customer email: ");
	                String email = scanner.nextLine();
	                System.out.print("Enter customer password: ");
	                String password = scanner.nextLine();
	                Customer customer = new Customer(0, name, email, password);
	                boolean success = orderRepo.createCustomer(customer);
	                System.out.println(success ? "Customer created successfully....!\n" : "*** Failed to create customer. ***");
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	            break;

	        case 2:
	            System.out.print("Enter customer ID to delete: ");
	            int customerId = scanner.nextInt();

	            try {
	                boolean success1 = orderRepo.deleteCustomer(customerId);
	                System.out.println(success1 ? "Customer deleted successfully.....!\n" : "*** Customer not found or could not be deleted. ***");
	            } catch (RuntimeException e) {
	                System.err.println("Error: " + e.getMessage());
	            }
	            break;

	        default:
	            System.out.println("*** Invalid option selected. ***");
	            break;
	    }
	}


    private static void addToCart() throws ClassNotFoundException,ProductNotFoundException,CustomerNotFoundException {
    	System.out.println("Select From The Options");
		System.out.println("-> 1.Add to Cart");
		System.out.println("-> 2.Delete From Cart");
		System.out.println("Enter Your Option:");
		int op =scanner.nextInt();
		switch(op) {
		case 1:
			try {
				System.out.print("Enter customer ID: ");
				int customerId = scanner.nextInt();
				System.out.print("Enter product ID: ");
				int productId = scanner.nextInt();
				System.out.print("Enter quantity: ");
				int quantity = scanner.nextInt();

				Customer customer = new Customer(customerId, "", "", "");
				Product product = new Product(productId, "", 0.0, "",0);
				boolean success = orderRepo.addToCart(customer, product, quantity);
				System.out.println(success ? "Product added to cart successfully......!\n" : "*** Failed to add product to cart. ***");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    break;
     
		case 2 :
			System.out.print("Enter customer ID: ");
			int customerId1 = scanner.nextInt();
			System.out.print("Enter product ID: ");
			int productId1 = scanner.nextInt();

			Customer customer1 = new Customer(customerId1, "", "", "");
			Product product1 = new Product(productId1, "", 0.0,"",0);
			boolean success1 = orderRepo.removeFromCart(customer1, product1);
			System.out.println(success1 ? "Product removed from cart successfully.....!\n" : "*** Failed to remove product from cart..! ***  \n**your cart may be Empty or Enter the Correct Product_Id**");
			break;
		}
    }

    private static void viewCart() throws ClassNotFoundException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        Customer customer = new Customer(customerId, "", "", "");
        List<Product> cartProducts = orderRepo.getAllFromCart(customer);

        if (cartProducts.isEmpty()) {
            System.out.println("** Your cart is empty..! **");
        } else {
        	System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.println("Cart Items:");
            for (Product product : cartProducts) {
                System.out.println(product);
                
            }System.out.println("------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void placeOrder() throws ClassNotFoundException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter shipping address: ");
        String shippingAddress = scanner.nextLine();

        Customer customer = new Customer(customerId, "", "", "");
        List<Map<Product, Integer>> orderList = new ArrayList<>();

        while (true) {
            System.out.print("Enter product ID (or) (Enter 0 to stop adding): ");
            int productId = scanner.nextInt();
            if (productId == 0) break;
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            Product product = new Product(productId, "", 0.0, "",0);
            Map<Product, Integer> orderItem = new HashMap<>();
            orderItem.put(product, quantity);
            orderList.add(orderItem);
        }

        boolean success = orderRepo.placeOrder(customer, orderList, shippingAddress);
        System.out.println(success ? "Order placed successfully.....!\n" : "*** Failed to place order. ***");
    }

    private static void viewOrders() throws ClassNotFoundException, CustomerNotFoundException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); 
        List<OrderDetail> orders = orderRepo.getOrdersByCustomer(customerId);

        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
        	System.out.println("---------------");
            System.out.println("Your Orders:");
            System.out.println("--------------------------------------------------------------------");
            for (OrderDetail order : orders) {
                Product product = order.getProduct(); 
                int quantity = order.getQuantity();
                System.out.println("Product Name: " + product.getName());
                System.out.println("Description : " + product.getDescription());
                System.out.println("Price       : ₹" + product.getPrice());
                System.out.println("Quantity    : " + quantity);
                System.out.println("--------------------------------------------------------------------");
            }
        }
        
   }


}