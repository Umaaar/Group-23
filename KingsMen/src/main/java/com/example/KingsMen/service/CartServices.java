package com.example.KingsMen.service;

public class CartServices{

    @Autowired
    private CartRepository cartRepo;
    public List<Cart> cartItemsList (Customer customer){
        return cartRepo.findByCustomer(customer);
    }
}