package com.example.KingsMen.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KingsMen.model.Sizes;
import com.example.KingsMen.repository.SizesRepository;
@Service
public class SizesService {
    
    @Autowired
    private SizesRepository sizeRepository;

    public List<Sizes> getAllSizes() {
        return sizeRepository.findAll();
    }

    public void addSize(Sizes size) {
        sizeRepository.save(size);
    }

    public void removeSizeById(long id) {
        sizeRepository.deleteById(id);
    }

    public Sizes getSizeById(long id) {
        return sizeRepository.findById(id).get();
    }

    public List<Sizes> getSizesByCategoryId(int categoryId) {
        return sizeRepository.findAllByCategory_Id(categoryId);
    }
    
}
