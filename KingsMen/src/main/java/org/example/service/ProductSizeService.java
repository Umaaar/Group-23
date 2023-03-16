package org.example.service;

import java.util.List;
import java.util.Optional;

import org.example.model.Product;
import org.example.model.ProductSize;
import org.example.model.ProductSizeId;
import org.example.model.Size;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSizeService {
    
@Autowired
ProductSizeRepository productSizeRepository;


   // Create
   public ProductSize createProductSize(Product product, Size size, int quantity) {
    ProductSize productSize = new ProductSize(product, size, quantity);
    return productSizeRepository.save(productSize);
}

// Read
public ProductSize getProductSizeById(ProductSizeId id) {
    Optional<ProductSize> optionalProductSize = productSizeRepository.findById(id);
    return optionalProductSize.orElse(null);
}

public List<ProductSize> getAllProductSizes() {
    return productSizeRepository.findAll();
}

// Update
public ProductSize updateProductSize(ProductSize productSize) {
    return productSizeRepository.saveAndFlush(productSize);
}

// Delete
public void deleteProductSizeById(ProductSizeId id) {
    productSizeRepository.deleteById(id);
}

public List<ProductSize> getProductSizesByProductId(Long id) {
    return productSizeRepository.findAllByProductId(id);
}
    
}
