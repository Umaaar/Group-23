package org.example.dto;


import lombok.Data;

@Data
public class OrderDTO {

    private Integer id;
    private String name;
    private String email;
    private String order_products;
    private Integer status;
    private Long total;

}
