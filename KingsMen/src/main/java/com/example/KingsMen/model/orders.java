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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_email() {
        return order_email;
    }

    public void setOrder_email(String order_email) {
        this.order_email = order_email;
    }

    public int getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(int order_product_id) {
        this.order_product_id = order_product_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getOrder_total() {
        return order_total;
    }

    public void setOrder_total(int order_total) {
        this.order_total = order_total;
    }

    @Override
    public String toString() {
        return "orders{" +
                "order_id=" + order_id +
                ", order_name='" + order_name + '\'' +
                ", order_email='" + order_email + '\'' +
                ", order_product_id=" + order_product_id +
                ", order_status='" + order_status + '\'' +
                ", order_total=" + order_total +
                '}';
    }
}
