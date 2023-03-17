package org.example.service;

import java.util.List;
import java.util.Optional;

import org.example.model.ProductSize;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSizeService {
    
@Autowired
ProductSizeRepository productSizeRepository;


   // Create
public ProductSize saveProductSize(ProductSize productSize) {
    return productSizeRepository.save(productSize);
}
// Read
public Optional<ProductSize> getProductSizeById(Long id) {
    return productSizeRepository.findById(id);
}

public List<ProductSize> getAllProductSizes() {
    return productSizeRepository.findAll();
}

// Delete
public void deleteProductSizeById(Long id) {
    productSizeRepository.deleteById(id);
}

public List<ProductSize> getProductSizesByProductId(Long id) {
    return productSizeRepository.findAllByProductId(id);
}

public List<ProductSize> getProductSizesBySizeId(Long id) {
    return productSizeRepository.findAllBySizeId(id);
    
}
}
