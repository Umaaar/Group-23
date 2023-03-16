package org.example.repository;

import java.util.List;

import org.example.model.ProductSize;
import org.example.model.ProductSizeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, ProductSizeId> {

    List<ProductSize> findAllByProductId(Long id);

    
}
