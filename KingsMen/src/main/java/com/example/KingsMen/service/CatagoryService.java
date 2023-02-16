package com.example.KingsMen.service;

import com.example.KingsMen.model.Category;
import com.example.KingsMen.repository.CatagoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CatagoryService {

    private final CatagoryRepository catagoryRepository;

    public CatagoryService(CatagoryRepository catagoryRepository) {
        this.catagoryRepository = catagoryRepository;
    }
    public void save(Category category) {
        catagoryRepository.save(category);
    }

    public List<Category> findAll() {
        return catagoryRepository.findAll();
    }





}
