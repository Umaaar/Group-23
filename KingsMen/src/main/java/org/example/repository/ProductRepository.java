package org.example.repository;


import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
 List<Product> findAllByCategory_Id(int id);
 List<Product> findAllByProductSizes_Id(long id);
 List<Product> findAllByStock(int stock);
 List<Product> findAllByOrderByPriceAsc();
 List<Product> findAllByOrderByPriceDesc();


 @Query(value = "select * from PRODUCT e where e.name like %:keyword% or e.description like %:keyword%" ,nativeQuery = true)
 List<Product> findByKeyword(@Param("keyword") String keyword );

@Modifying
@Query("UPDATE Product p SET p.stock = p.stock - :stock WHERE p.id = :productId")
int decreaseStock(@Param("productId") Long productId, @Param("stock") int stock);


@Query(value = "SELECT * FROM Product ORDER BY RAND() LIMIT 1", nativeQuery = true)
Product findRandomProduct();


}
