package org.example.model;

import java.util.List;

public class Order {
    private int order_id;
    private String order_name;
    private String order_email;
    private List<Product> order_products;
    private String order_status;
    private int order_total;

    public Order(int order_id, String order_name, String order_email, String order_status, int order_total) {
        this.order_id = order_id;
        this.order_name = order_name;
        this.order_email = order_email;

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
                ", order_status='" + order_status + '\'' +
                ", order_total=" + order_total +
                '}';
    }
}
