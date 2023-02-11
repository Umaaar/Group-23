package com.example.KingsMen.model;

public class products {
    private int product_ID;
    private String product_title;
    private String product_imgs;
    private String product_description;
    private String product_cat;
    private int product_price;

    public products(int product_ID, String product_title, String product_imgs, String product_description, String product_cat, int product_price) {
        this.product_ID = product_ID;
        this.product_title = product_title;
        this.product_imgs = product_imgs;
        this.product_description = product_description;
        this.product_cat = product_cat;
        this.product_price = product_price;
    }
}
