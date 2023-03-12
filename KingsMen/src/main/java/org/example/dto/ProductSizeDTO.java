package org.example.dto;

import lombok.Data;

@Data
public class ProductSizeDTO {

    private Long id;
    private Long productId;
    private Long sizeId;
    private int quantity;

    
}
