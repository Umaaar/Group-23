package org.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.example.model.ProductSize;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    public Optional<ProductSize> decreaseQuanityForProductSizeObj(Long productID, Long sizeID){
        return productSizeRepository.findByProductIdAndSizeId(productID,sizeID);
    }

//    @Modifying
//    @Query("UPDATE Product p SET p.stock = p.stock - :stock WHERE p.id = :productId")
//    int decreaseStock(@Param("productId") Long productId, @Param("stock") int stock) {
//        return 0;
//    }

    @Transactional
    public void decreasingStock(Long productId, Long size, int quanitity) {
        productSizeRepository.decreaseQuantity(productId, size,quanitity);
    }

    public Long getProductSizeIdbySizeIdAndProductId(Long sizeId, Long productId){
        return productSizeRepository.findByProductIdAndSizeId(productId, sizeId).get().getId();
    }


}
