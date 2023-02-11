package com.example.KingsMen.model;

public class orders {
    private int order_id;
    private String order_name;
    private String order_email;
    private int order_product_id;
    private String order_status;
    private int order_total;

    public orders(int order_id, String order_name, String order_email, int order_product_id, String order_status, int order_total) {
        this.order_id = order_id;
        this.order_name = order_name;
        this.order_email = order_email;
        this.order_product_id = order_product_id;
        this.order_status = order_status;
        this.order_total = order_total;
    }
}
