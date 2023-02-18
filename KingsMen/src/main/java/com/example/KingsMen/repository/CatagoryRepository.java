package com.example.KingsMen.repository;

import com.example.KingsMen.model.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepository extends JpaRepository<Catagory,Integer> {
//    Catagory findByCategoryName(String name);
}
