package com.example.KingsMen.service;

import com.example.KingsMen.model.Product;
import com.example.KingsMen.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProducts(){ return productRepository.findAll();}
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void removeProductById(long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductByID(long id){
        return  productRepository.findById(id);
    }
//    public List<Product> getAllProductsByCatagoryId(int id){
//        return productRepository.findAllCategory_Id(id);
//    }


}
