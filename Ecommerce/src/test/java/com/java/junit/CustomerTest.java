package com.java.junit;

import org.junit.jupiter.api.Test;
import com.java.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testNoArgsConstructor() {
        Customer customer = new Customer();
        assertNotNull(customer);
    }

    @Test
    public void testAllArgsConstructor() {
        Customer customer = new Customer(1, "Gopal", "gopal@example.com", "securePass123");
        assertEquals(1, customer.getCustomerId());
        assertEquals("Gopal", customer.getName());
        assertEquals("gopal@example.com", customer.getEmail());
        assertEquals("securePass123", customer.getPassword());
    }


    @Test
    public void testSetters() {
        Customer customer = new Customer();

        customer.setCustomerId(101);
        customer.setName("Rahul");
        customer.setEmail("rahul@example.com");
        customer.setPassword("pass456");

    }

    @Test
    public void testGetters() {
        Customer customer = new Customer();
        customer.setCustomerId(202);
        customer.setName("Meena");
        customer.setEmail("meena@example.com");
        customer.setPassword("meenaPass");

        assertEquals(202, customer.getCustomerId());
        assertEquals("Meena", customer.getName());
        assertEquals("meena@example.com", customer.getEmail());
        assertEquals("meenaPass", customer.getPassword());
    }

    @Test
    public void testToString() {
        Customer customer = new Customer(3, "Anjali", "anjali@example.com", "pass321");
        String str = customer.toString();

        assertTrue(str.contains("customerId=3"));
        assertTrue(str.contains("name=Anjali"));
        assertTrue(str.contains("email=anjali@example.com"));
        assertTrue(str.contains("password=pass321"));
    }
}
