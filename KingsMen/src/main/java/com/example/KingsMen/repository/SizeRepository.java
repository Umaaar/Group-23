package com.example.KingsMen.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KingsMen.model.Size;


public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findAllByCategory_Id(int id);

}
