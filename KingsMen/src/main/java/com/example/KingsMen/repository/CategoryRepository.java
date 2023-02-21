package com.example.KingsMen.repository;

import com.example.KingsMen.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
//    Catagory findByCategoryName(String name);
}
