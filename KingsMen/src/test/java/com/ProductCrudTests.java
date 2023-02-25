/*package com;

import org.aspectj.lang.annotation.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.example.KingsMen.KingsMenApplication;
import com.example.KingsMen.repository.ProductRepository;

public class ProductCrudTests {
    
@RunWith(KingsMenApplication.class)
@SpringBootTest
public class ProductCRUDTest {
    
    @Autowired
    private ApplicationContext context;
    
    @MockBean
    private ProductRepository productRepository;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateProduct() {
        Product product = new Product("Test Product", 10.0);
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getPrice()).isEqualTo(10.0);
    }
    
    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product("Test Product", 10.0);
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productRepository.findById(productId);
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        assertThat(foundProduct.get().getPrice()).isEqualTo(10.0);
    }
    
    // Add more tests for update and delete operations
    
}

}
*/