package org.example.dto;

import java.util.List;

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
    private List<Long> productSizeIds; 
    private List<ProductSizeDTO> productSizes; // new field for ProductSizeDTO objects

    public List<ProductSizeDTO> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSizeDTO> productSizes) {
        this.productSizes = productSizes;
    }
 

    

}
