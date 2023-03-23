package org.example.repository;

import java.util.List;

import org.example.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

    List<ProductSize> findAllByProductId(Long id);
    List<ProductSize> findAllBySizeId(Long id);
    
    @Modifying
@Query("UPDATE ProductSize p SET p.quantity = p.quantity - :quantity WHERE p.id = :productsizeid")
int decreaseStock(@Param("productsizeid") Long productSizeId, @Param("quantity") int quantity);

    
}
