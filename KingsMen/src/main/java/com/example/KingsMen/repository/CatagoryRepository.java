package com.example.KingsMen.repository;

import com.example.KingsMen.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepository extends JpaRepository<Category,Integer> {
    Category findByCategoryName(String name);
}
