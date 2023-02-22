package com.example.KingsMen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KingsMen.repository.SizeRepository;
import com.example.KingsMen.model.Size;

@Service
public class SizeService {

    @Autowired
  
    private SizeRepository sizeRepository;

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public void addSize(Size size) {
        sizeRepository.save(size);
    }

    public void removeSizeById(int id) {
        sizeRepository.deleteById(id);
    }

    public Optional<Size> getSizeById(int id) {
        return sizeRepository.findById(id);
    }

    public List<Size> getSizesByCategoryId(int categoryId) {
        return sizeRepository.findAllByCategory_Id(categoryId);
    }
    
}
