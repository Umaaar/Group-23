package com.example.KingsMen;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartTests{
    testAddOneCartItem
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddACartItem(){
        Product product = entityManager.find(Product.class, primaryKey); /* Change 'primaryKey' */
        Customer customer = entityManager.find(Customer.class, primaryKey); /* Change 'primaryKey' */

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
        customer.setId(id); /* Change 'id' to customer id number */

        List<Cart> cartItems = cartRepo.findByCustomer(customer);
        assertEquals(2, cartItems.size())
    }
}