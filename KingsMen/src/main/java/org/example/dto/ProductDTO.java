package org.example.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private String imageName;
    private String description;
    private String size;
    private int quantity;
}
