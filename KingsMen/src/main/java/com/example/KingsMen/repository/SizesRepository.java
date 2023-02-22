package com.example.KingsMen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KingsMen.model.Sizes;


public interface SizesRepository extends JpaRepository<Sizes, Long> {
    List<Sizes> findAllByCategory_Id(int id);

}
