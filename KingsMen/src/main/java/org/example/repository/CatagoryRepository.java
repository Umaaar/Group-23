package org.example.repository;


import org.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepository extends JpaRepository<Category,Integer> {
}
