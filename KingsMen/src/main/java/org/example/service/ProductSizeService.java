package org.example.service;

import java.util.List;

import org.example.model.ProductSize;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSizeService {
    
    @Autowired
    private ProductSizeRepository productSizeRepository;

    public ProductSizeService(ProductSizeRepository productSizeRepository) {
        this.productSizeRepository = productSizeRepository;
    }

    public ProductSize save(ProductSize productSize) {
        return productSizeRepository.save(productSize);
    }

    public ProductSize findById(Long id) {
        return productSizeRepository.findById(id).get();
    }

    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    public void deleteById(Long id) {
        productSizeRepository.deleteById(id);
    }

    public List<ProductSize> getProductSizesByProductId(Long productId) {
        return productSizeRepository.getProductSizesByProductId(productId);
    }

    public List<ProductSize> getProductSizesBySizeId(Long sizeId) {
        return productSizeRepository.getProductSizesBySizeId(sizeId);
    }

    

  

  
}
