package org.example.repository;

import java.util.List;

import org.example.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    List<ProductSize> findByProductId(Long productId);
    List<ProductSize> findBySizeId(Long sizeId);
}