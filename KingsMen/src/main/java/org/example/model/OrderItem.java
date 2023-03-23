package org.example.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_DETAILS_ID")
    private OrderDetails orderDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "product_size_id")
    private ProductSize productSize;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "SIZE")
    private String size;
    @Column(name = "order_id")
    private int orderID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct() {
        this.product = new Product();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}