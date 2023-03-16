package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "product_size")
public class ProductSize {

    @EmbeddedId
    private ProductSizeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sizeId")
    private Size size;

    private int quantity;

    public ProductSize() {
    }

    public ProductSize(Product product, Size size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.id = new ProductSizeId(product.getId(), size.getId());
    }

    public ProductSizeId getId() {
        return id;
    }

    public void setId(ProductSizeId id) {
        this.id = id;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
