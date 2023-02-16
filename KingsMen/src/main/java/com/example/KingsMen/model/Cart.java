package com.example.KingsMen.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name='cart')
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name='cart_id')
    private int id;

    @ManytoOne
    @JoinColumn(name='product_id')
    private Product product;

    @ManytoOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    private int quantity;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}