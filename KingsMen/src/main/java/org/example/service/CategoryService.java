package org.example.service;



import org.example.model.Category;
import org.example.repository.CatagoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    @Autowired
    CatagoryRepository categoryRepository;
    public List<Category> getAllCategory(){

        return categoryRepository.findAll();
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void removeCategoryById(int id){ categoryRepository.deleteById(id);}
    
    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
    public int getCategoryCount(){
        return (int) categoryRepository.count();
    }

 







}
