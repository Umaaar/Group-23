package com.example.KingsMen.service;

import com.example.KingsMen.model.Product;
import com.example.KingsMen.repository.ProductRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
}


    /*public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product findById(long id) {
        return productRepository.findById(id);
    }

 
    public void save(Product product) {
        productRepository.save(product);
    }


    public List<Product> findAllByOrderByIdAsc() {
        return productRepository.findAllByOrderByIdAsc();
    }*/

}
