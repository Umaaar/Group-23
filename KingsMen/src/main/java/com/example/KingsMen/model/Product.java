package com.example.KingsMen.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Objects;

    @Data
    @Entity
    @Table(name = "product")
    public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "catagory_id", referencedColumnName = "category_id")
    private Catagory category;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "Stock")
    private int stock;
    
    @Column(name = "image")
    private String imageName;

    @Column(name = "price")
    private double price;
    
    @Column(name = "size")
    private String size;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        return id == product.getId();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


//     public Product(String name, String description, double price, int stock, String imageName) {
//         this.name = name;
//         this.description = description;
//         this.price = price;
//         this.stock = stock;
//         this.imageName = imageName;
//     }
// }

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public Category getCategory() {
//         return category;
//     }

//     public void setCategory(Category category) {
//         this.category = category;
//     }

//     public int getStock() {
//         return stock;
//     }

//     public void setStock(int stock) {
//         this.stock = stock;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public void setPrice(double price) {
//         this.price = price;
//     }

//     public String getSize() {
//         return size;
//     }

//     public void setSize(String size) {
//         this.size = String.valueOf(size);
//     }

//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public String getImageName() {
//         return imageName;
//     }

//     public void setImageName(String imageName) {
//         this.imageName = imageName;
//     }
// }