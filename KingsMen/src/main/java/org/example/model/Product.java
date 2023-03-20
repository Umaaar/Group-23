package org.example.model;

import lombok.Data;

import java.util.List;

import javax.persistence.*;


@Entity
@Data
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
   
   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_size_id", referencedColumnName = "product_size_id")
    private ProductSize productSizes;

    private double price;

    private int stock;

    private String imageName;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductSize getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(ProductSize productSizes) {
        this.productSizes = productSizes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
        }

    public int getQuantity(){
        return stock;
    }

    public void setQuantity(int quantity){
        stock = quantity;
    }

    public void setStock(int stock) {
        this.stock = stock;
        
    }
    

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    public double getQuantityTimesPrice() {
        double qty = (getPrice() * getQuantity());
        return qty;
    }

}
