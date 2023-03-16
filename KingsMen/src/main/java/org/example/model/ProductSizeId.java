package org.example.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductSizeId implements Serializable{
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "size_id")
    private Long sizeId;

    public ProductSizeId() {
    }

    public ProductSizeId(Long productId, Long sizeId) {
        this.productId = productId;
        this.sizeId = sizeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSizeId that = (ProductSizeId) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        return sizeId != null ? sizeId.equals(that.sizeId) : that.sizeId == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (sizeId != null ? sizeId.hashCode() : 0);
        return result;
    }

}
