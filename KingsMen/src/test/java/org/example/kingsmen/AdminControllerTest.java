package org.example.kingsmen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.given;

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
    private MultipartFile multipartFile;

    
    @Test
    public void testAdminHome() {
        // Setup
        when(customUserDetailService.getUserCount()).thenReturn((int)10L);
        
        // Execute
        String viewName = adminController.adminHome(customUserDetail, model);
        
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
    
    @Test
    public void testGetCat() {
        // Set up test data
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.getAllCategory()).thenReturn(categories);
        Model model = new ExtendedModelMap();

        // Call the method under test
        String viewName = adminController.getCat(model);

        // Verify the results
        assertEquals("/backend-views/categories", viewName);
        assertEquals(categories, model.getAttribute("categories"));
    }

    @Test
    public void testPostCreateCat() {
        // Set up test data
        Model model = new ExtendedModelMap();

        // Call the method under test
        String viewName = adminController.postCreateCat(model);

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
        String viewName = adminController.productspage(model);

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
        String viewName = adminController.createProducts(model);

        // then
        verify(model).addAttribute(eq("productDTO"), any(ProductDTO.class));
        verify(model).addAttribute(eq("categories"), eq(categories));
        verify(model).addAttribute(eq("sizes"), eq(sizes));
        assertEquals("/backend-views/products-create", viewName);
    }
    
}

