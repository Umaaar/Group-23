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

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_imgs() {
        return product_imgs;
    }

    public void setProduct_imgs(String product_imgs) {
        this.product_imgs = product_imgs;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    @Override
    public String toString() {
        return "products{" +
                "product_ID=" + product_ID +
                ", product_title='" + product_title + '\'' +
                ", product_imgs='" + product_imgs + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_cat='" + product_cat + '\'' +
                ", product_price=" + product_price +
                '}';
    }
}
