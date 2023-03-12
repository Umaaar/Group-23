package org.example.repository;


import java.util.List;

import org.example.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size,Long> {
    List<Size> findByCategoryId(int categoryId);

}
