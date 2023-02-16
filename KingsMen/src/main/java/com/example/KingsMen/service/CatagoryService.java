package com.example.KingsMen.service;

import com.example.KingsMen.model.Catagory;
import com.example.KingsMen.repository.CatagoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CatagoryService {

    private final CatagoryRepository catagoryRepository;

    public CatagoryService(CatagoryRepository catagoryRepository) {
        this.catagoryRepository = catagoryRepository;
    }
    public void save(Catagory catagory) {
        catagoryRepository.save(catagory);
    }

    public List<Catagory> findAll() {
        return catagoryRepository.findAll();
    }





}
