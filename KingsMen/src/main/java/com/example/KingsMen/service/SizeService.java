package com.example.KingsMen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.KingsMen.repository.SizeRepository;
import com.example.KingsMen.model.Size;

public class SizeService {

    @Autowired
    static
    SizeRepository sizeRepository;
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }
    
}
