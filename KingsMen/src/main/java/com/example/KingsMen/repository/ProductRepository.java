package com.example.KingsMen.repository;

import com.example.KingsMen.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    List<Product> findAllCategory_Id(int id);
}
