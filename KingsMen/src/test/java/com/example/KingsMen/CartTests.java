/*
package com.example.KingsMen;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.KingsMen.model.Product;
import com.example.KingsMen.repository.CartRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartTests{
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    
    public void testAddACartItem(){
        Product product = entityManager.find(Product.class, 1); */
/* Change 'primaryKey' *//*

        Customer customer = entityManager.find(Customer.class, 5); */
/* Change 'primaryKey' *//*


        Cart newItem = new Cart();
        newItem.setCustomer(customer);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        Cart saveItem = cartRepo.save(newItem);
        assertTrue(saveItem.getId() > 0);
    }

    @Test
    public void testGetItemsByCustomer(){
        Customer customer = new Customer();
        customer.setId(id); */
/* Change 'id' to customer id number *//*


        List<Cart> cartItems = cartRepo.findByCustomer(customer);
        assertEquals(2, cartItems.size());
    }
}*/
