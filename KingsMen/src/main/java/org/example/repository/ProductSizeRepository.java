package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

    List<ProductSize> findAllByProductId(Long id);
    List<ProductSize> findAllBySizeId(Long id);
    Optional<ProductSize> findByProductIdAndSizeId(long productId, long sizeId);
    
    // @Modifying
    // @Query("UPDATE PRODUCT_SIZE p SET p.QUANTITY = p.QUANTITY - :quantity WHERE p.ID = :productsizeid")
    // int decreaseStock(@Param("productsizeid") Long productSizeId, @Param("quantity") int quantity);
    
}
