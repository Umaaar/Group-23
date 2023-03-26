package org.example.kingsmen;

import org.aspectj.lang.annotation.Before;
import org.example.controller.ProductsController;
import org.example.model.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @Mock
    ProductSizeService productSizeService;

    @Mock
    SizeService sizeService;

    @InjectMocks
    ProductsController productsController;

    @Mock
    Model model;

    @Test
    public void testGetProductsPage() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Product 1 Description");
        product1.setPrice(100.0);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Product 2 Description");
        product2.setPrice(200.0);
        productList.add(product1);
        productList.add(product2);
        when(productService.findByKeyword(anyString())).thenReturn(productList);

        String result = productsController.getProductsPage(model, "asc", "test");

        assertEquals("frontend-views/product-page", result);
    }

    @Test
    public void testGetProductsByCategory() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Product 1 Description");
        product1.setPrice(100.0);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Product 2 Description");
        product2.setPrice(200.0);
        productList.add(product1);
        productList.add(product2);
        when(productService.getProductsByCategoryId(1)).thenReturn(productList);

        String result = productsController.getProductsByCategory(model, 1, "desc");

        assertEquals("/frontend-views/product-page", result);
    }

    @Test
    public void testGetProductDetailPage() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Product 1 Description");
        product.setPrice(100.0);
        Optional<Product> optionalProduct = Optional.of(product);
        when(productService.getProductById(1L)).thenReturn(optionalProduct);

        String result = productsController.getProductDetailPage(model, 1L);

        assertEquals("frontend-views/product-detail-page", result);
    }

}
