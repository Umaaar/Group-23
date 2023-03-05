package org.example.repository;


import org.example.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDetails,Integer> {

    @Query(value = "select * from ORDER_DETAILS e where e.email like %:keyword% or e.name like %:keyword%" ,nativeQuery = true)
    List<OrderDetails> findByKeyword(@Param("keyword") String keyword );
    
}
