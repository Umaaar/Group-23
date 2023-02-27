package org.example.service;


import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(long id) {
        productRepository.deleteById(id);
    }


    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategoryId(int categoryId) {
            return productRepository.findAllByCategory_Id(categoryId);
    }

    public int getProductCount() {
        return (int) productRepository.count();
    }


    /*public int getInStockProductCount() {
        return (int) productRepository.countByInStock(true);
    }

    public int getOutOfStockProductCount() {
        return (int) productRepository.countByInStock(false);
    }
    */

 

        


}
