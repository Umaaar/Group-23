package com.example.KingsMen.service;

import com.example.KingsMen.model.Product;
import com.example.KingsMen.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    

    public List<Product> getProductsByCategoryId(int categoryId) {
            return productRepository.findAllByCategory_Id(categoryId);
    }

    public List<Product> getProductsBySizeId(long sizeId) {
        return productRepository.findAllBySize_Id(sizeId);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

        


}
