package org.example.service;


import org.example.model.Product;
import org.example.model.ProductSize;
import org.example.repository.ProductRepository;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSizeRepository productSizeRepository;

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

    public List<Product> getProductsSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }
    
    public List<Product> getProductsSortedByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }


    /*public int getInStockProductCount() {
        return (int) productRepository.countByInStock(true);
    }

    public int getOutOfStockProductCount() {
        return (int) productRepository.countByInStock(false);
    }
    */

    public Integer getProductCount() {
        return Math.toIntExact(productRepository.count());
    }
    public Integer getInStockProducts(){
        List<Product> totalProducts = productRepository.findAll();
        List<Product> inStockProducts = new ArrayList<>();

        for (Product product : totalProducts) {
            if (product.getStock() > 1) {
                inStockProducts.add(product);
            }
        }
        return inStockProducts.size();
    }

    public Integer getOutOfStockProducts(){
        List<Product> outOfStockProducts = productRepository.findAllByStock(0);
        return  outOfStockProducts.size();
    }

    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    @Transactional
    public void decreasingStock(Long productId, int stock) {
        productRepository.decreaseStock(productId, stock);
    }

    public Product getProductWithSizes(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            List<ProductSize> productSizes = productSizeRepository.findByProductId(productId);
            product.setProductSizes(productSizes);
        }
        return product;
    }   


}
