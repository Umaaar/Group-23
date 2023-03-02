package org.example.repository;


import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
 List<Product> findAllByCategory_Id(int id);
 List<Product> findAllByStock(int stock);


 @Query(value = "select * from PRODUCT e where e.name like %:keyword% or e.description like %:keyword%" ,nativeQuery = true)
 List<Product> findByKeyword(@Param("keyword") String keyword );
}
