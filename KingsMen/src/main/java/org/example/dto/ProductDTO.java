package org.example.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int categoryId;
    private String description;
    private double price;
    private int quantity;
    private int stock;
    private String imageName;
    private String size;

    

}
