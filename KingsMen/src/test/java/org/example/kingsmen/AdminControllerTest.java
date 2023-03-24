package org.example.kingsmen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.example.controller.AdminController;
import org.example.dto.ProductDTO;
import org.example.model.Category;
import org.example.model.CustomUserDetail;
import org.example.model.Product;
import org.example.model.Size;
import org.example.service.CategoryService;
import org.example.service.CustomUserDetailService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;



@ExtendWith(MockitoExtension.class)

public class AdminControllerTest {
    
    @Mock
    private ProductService productService;
    
    @Mock
    private CategoryService categoryService;
    
    @Mock
    private SizeService sizeService;
    
    @Mock
    private ProductSizeService productSizeService;
    
    @Mock
    private CustomUserDetailService customUserDetailService;
    
    @Mock
    private OrderService orderService;
    
    @InjectMocks
    private AdminController adminController;
    
    @Mock
    private CustomUserDetail customUserDetail;
    
    @Mock
    private Category category;
    
    @Mock
    private Model model;

    @Mock
    private ProductDTO productDTO;
    
    @Mock
    private MultipartFile file;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Mock
    HttpServletResponse response;

    
    @Test
    public void testAdminHome() {
        // Setup
        when(customUserDetailService.getUserCount()).thenReturn((int)10L);
        
        // Execute
        String viewName = adminController.adminHome(customUserDetail, response, model);
        
        // Verify
        assertEquals("/backend-views/admin-index", viewName);
        //verify(model).addAttribute(eq("adminname"), anyString());
        verify(model).addAttribute(eq("total_customers"), eq("10"));
        verify(model).addAttribute(eq("total_catagories"), anyString());
        verify(model).addAttribute(eq("total_products"), anyString());
        verify(model).addAttribute(eq("total_in_stock_products"), anyString());
        verify(model).addAttribute(eq("total_in_out_of_stock_products"), anyString());
        verify(model).addAttribute(eq("total_orders"), anyString());
    }
    
    /* Category Crud Junit Tests */
    @Test
    public void testGetCat() {
        // Set up test data
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.getAllCategory()).thenReturn(categories);
        Model model = new ExtendedModelMap();

        // Call the method under test
        String viewName = adminController.getCat(customUserDetail, model);

        // Verify the results
        assertEquals("/backend-views/categories", viewName);
        assertEquals(categories, model.getAttribute("categories"));
    }

    @Test
    public void testPostCreateCat() {
        // Set up test data
        Model model = new ExtendedModelMap();

        // Call the method under test
        String viewName = adminController.postCreateCat(customUserDetail, model);

        // Verify the results
        assertEquals("/backend-views/category-create", viewName);
        assertNotNull(model.getAttribute("category"));
    }

    @Test
    public void testAdminCreateCat() {
        // Set up test data
        Category category = new Category();
        String expectedRedirect = "redirect:/admin/categories";

        // Call the method under test
        String actualRedirect = adminController.adminCreateCat(category);

        // Verify the results
        assertEquals(expectedRedirect, actualRedirect);
        verify(categoryService, times(1)).addCategory(category);
    }

    @Test
    public void testDeleteCategory() {
        // Set up test data
        int categoryId = 1;
        String expectedRedirect = "redirect:/admin/categories";

        // Call the method under test
        String actualRedirect = adminController.deleteCategory(categoryId);

        // Verify the results
        assertEquals(expectedRedirect, actualRedirect);
        verify(categoryService, times(1)).removeCategoryById(categoryId);
    }

    @Test
    public void testEditCategory() {
        // Set up test data
        int categoryId = 1;
        Optional<Category> category = Optional.of(new Category());
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);
        Model model = new ExtendedModelMap();

        // Call the method under test
        String viewName = adminController.editCategory(categoryId, model);

        // Verify the results
        assertEquals("/backend-views/category-create", viewName);
        assertEquals(category.get(), model.getAttribute("category"));
    }
    /* End of Category Crud Tests */

    /* Product Crud Junit Tests */

    @Test
    public void testProducts() {
        // given
        String keyword = "test";
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        given(productService.findByKeyword(keyword)).willReturn(products);

        // when
        String viewName = adminController.products(model, keyword);

        // then
        verify(model).addAttribute(eq("products"), eq(products));
        assertEquals("/backend-views/products", viewName);
    }

    @Test
    public void testProducts_noKeyword() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        given(productService.getAllProduct()).willReturn(products);

        // when
        String viewName = adminController.products(model, null);

        // then
        verify(model).addAttribute(eq("products"), eq(products));
        assertEquals("/backend-views/products", viewName);
    }

    @Test
    public void testProductspage() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        given(productService.getAllProduct()).willReturn(products);
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size());
        given(sizeService.getAllSizes()).willReturn(sizes);

        // when
        String viewName = adminController.productspage(customUserDetail, model);

        // then
        verify(model).addAttribute(eq("products"), eq(products));
        verify(model).addAttribute(eq("sizes"), eq(sizes));
        assertEquals("/backend-views/products", viewName);
    }

    @Test
    public void testCreateProducts() {
        // given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        given(categoryService.getAllCategory()).willReturn(categories);
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size());
        given(sizeService.getAllSizes()).willReturn(sizes);

        // when
        String viewName = adminController.createProducts(customUserDetail, model);

        // then
        verify(model).addAttribute(eq("productDTO"), any(ProductDTO.class));
        verify(model).addAttribute(eq("categories"), eq(categories));
        verify(model).addAttribute(eq("sizes"), eq(sizes));
        assertEquals("/backend-views/products-create", viewName);
    }

  
    
    @Test
    public void testCreateOrUpdateProduct_updateExistingProduct() throws Exception {
        Long productId = 1L;
        String productName = "Test Product";
        Integer categoryId = 1;
        String description = "This is a test product";
        Double price = 19.99;
        String imageName = "test-image.png";

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName(productName);
        Category category = new Category();
        category.setId(categoryId);
        existingProduct.setCategory(category);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setImageName(imageName);

        when(productDTO.getId()).thenReturn(productId);
        when(productService.getProductById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(file.isEmpty()).thenReturn(true);
        when(productDTO.getName()).thenReturn(productName);
        when(productDTO.getCategoryId()).thenReturn(categoryId);
        when(productDTO.getDescription()).thenReturn(description);
        when(productDTO.getPrice()).thenReturn(price);
       // when(productDTO.getImageName()).thenReturn(imageName);

        String result = adminController.createOrUpdateProduct(productDTO, file, imageName);

        assertEquals("redirect:/admin/products", result);
        assertEquals(productName, existingProduct.getName());
        assertEquals(category, existingProduct.getCategory());
        assertEquals(description, existingProduct.getDescription());
        assertEquals(price, existingProduct.getPrice());
        assertEquals(imageName, existingProduct.getImageName());
    }

    @Test
    public void testCreateOrUpdateProduct_createNewProduct() throws Exception {
        Long productId = null;
        String productName = "Test Product";
        Integer categoryId = 1;
        String description = "This is a test product";
        Double price = 19.99;
        String imageName = "test-image.png";

        when(productDTO.getId()).thenReturn(productId);
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(file.isEmpty()).thenReturn(true);
        when(productDTO.getName()).thenReturn(productName);
        when(productDTO.getCategoryId()).thenReturn(categoryId);
        when(productDTO.getDescription()).thenReturn(description);
        when(productDTO.getPrice()).thenReturn(price);
       // when(productDTO.getImageName()).thenReturn(imageName);

        String result = adminController.createOrUpdateProduct(productDTO, file, imageName);

        assertEquals("redirect:/admin/products", result);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(productService, times(1)).addProduct(argument.capture());
        assertEquals(productName, argument.getValue().getName());
        assertEquals(category, argument.getValue().getCategory());
        assertEquals(description, argument.getValue().getDescription());
        assertEquals(price, argument.getValue().getPrice());
        assertEquals(imageName, argument.getValue().getImageName());
    }

    @Test
public void testCreateOrUpdateProduct_productNotFound() throws Exception {
Long productId = 1L;
String imageName = "test-image.png";
    
    when(productDTO.getId()).thenReturn(productId);
    when(productService.getProductById(productId)).thenReturn(Optional.empty());

    String result = adminController.createOrUpdateProduct(productDTO, file, imageName);

    assertEquals("redirect:/admin/products?error=productNotFound", result);
    verify(productService, times(0)).addProduct(any(Product.class));
}

@Test
public void testUpdateProductGet_productFound() throws Exception {
    Long productId = 1L;

    Product product = new Product();
    product.setId(productId);
    product.setName("Test Product");
    Category category = new Category();
    category.setId(1);
    product.setCategory(category);
    product.setDescription("This is a test product");
    product.setPrice(19.99);
    product.setStock(10);
    product.setImageName("test-image.png");

    when(productService.getProductById(productId)).thenReturn(Optional.of(product));
    when(categoryService.getAllCategory()).thenReturn(Arrays.asList(new Category()));

    String result = adminController.updateProductGet(productId, model);

    assertEquals("/backend-views/products-create", result);
    verify(model, times(1)).addAttribute(eq("sizes"), anyList());
    verify(model, times(1)).addAttribute(eq("productDTO"), any(ProductDTO.class));
    verify(model, times(1)).addAttribute(eq("categories"), anyList());
    verify(productService, times(1)).getProductById(productId);
    verify(categoryService, times(1)).getAllCategory();
}

@Test
public void testUpdateProductGet_productNotFound() throws Exception {
    Long productId = 1L;

    when(productService.getProductById(productId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> {
        adminController.updateProductGet(productId, model);
    });

    verify(productService, times(1)).getProductById(productId);
}

@Test
public void testDeleteProduct_productFound() throws Exception {
    Long productId = 1L;

    String result = adminController.deleteProduct(productId);

    assertEquals("redirect:/admin/products", result);
    verify(productService, times(1)).removeProductById(productId);
}

@Test
public void testDeleteProduct_productNotFound() throws Exception {
    Long productId = 1L;

    doThrow(new NoSuchElementException()).when(productService).removeProductById(productId);

    assertThrows(NoSuchElementException.class, () -> {
        adminController.deleteProduct(productId);
    });

    verify(productService, times(1)).removeProductById(productId);
}

/* End of Product Crud Tests */

/* Start of ProductSize Crud Tests */



/* End of ProductSize Crud Tests */

}

