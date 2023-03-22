package org.example.dto;


import lombok.Data;
import org.example.model.OrderItem;

import java.util.List;

@Data
public class OrderDTO {

    private Integer id;
    private String name;
    private String email;
    private String order_products;
    private Integer status;
    private Double total;

    private List<OrderItem> orderItems;

}
