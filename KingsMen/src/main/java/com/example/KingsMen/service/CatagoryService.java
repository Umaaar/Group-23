package com.example.KingsMen.service;


import com.example.KingsMen.model.Category;
import com.example.KingsMen.repository.CatagoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CatagoryService {
    @Autowired
    CatagoryRepository catagoryRepository;
    public List<Category> getAllCategory(){

        return catagoryRepository.findAll();
    }
    public void addCategory(Category category){
        catagoryRepository.save(category);
    }

    public void removeCategoryById(int id){ catagoryRepository.deleteById(id);}
    public Optional<Category> getCategoryById(int id){
        return catagoryRepository.findById(id);
    }

   //public CatagoryService(CatagoryRepository catagoryRepository) {
      // this.catagoryRepository = catagoryRepository;
   //}







}
