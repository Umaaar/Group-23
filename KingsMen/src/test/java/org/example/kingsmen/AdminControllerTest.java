package org.example.kingsmen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.example.controller.AdminController;
import org.example.dto.OrderDTO;
import org.example.dto.ProductDTO;
import org.example.dto.ProductSizeDTO;
import org.example.model.Category;
import org.example.model.CustomUserDetail;
import org.example.model.OrderDetails;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.example.model.ProductSize;
import org.example.model.Size;
import org.example.model.User;
import org.example.service.CategoryService;
import org.example.service.CustomUserDetailService;
import org.example.service.OrderItemService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



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


    @Mock
    private OrderItemService orderItemService;
    
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
    private ProductSizeDTO productSizeDTO;
    
    @Mock
    private MultipartFile file;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Mock
    HttpServletResponse response;

    
    @Mock
    private RedirectAttributes redirectAttributes;

    
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
        verify(model).addAttribute(eq("total_low_stock_products"), anyString());
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
        String viewName = adminController.editCategory(categoryId, model, customUserDetail);

        // Verify the results
        assertEquals("/backend-views/category-create", viewName);
        assertEquals(category.get(), model.getAttribute("category"));
    }
    /* End of Category Crud Tests */

    /* Product Crud Junit Tests */

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
        String viewName = adminController.productspage(model, customUserDetail);

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

    String result = adminController.updateProductGet(productId, model, customUserDetail);

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
        adminController.updateProductGet(productId, model, customUserDetail);
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

@Test
public void testProductSize_productFound() {
    Long productId = 1L;
    Product product = new Product();
    product.setId(productId);
    List<ProductSize> productSizes = new ArrayList<>();
    productSizes.add(new ProductSize());

    when(productService.getProductById(productId)).thenReturn(Optional.of(product));
    when(productSizeService.getProductSizesByProductId(productId)).thenReturn(productSizes);

    String result = adminController.productSize(productId, model, redirectAttributes);

    verify(productService, times(1)).getProductById(productId);
    verify(productSizeService, times(1)).getProductSizesByProductId(productId);
    verify(model, times(1)).addAttribute("product", product);
    verify(model, times(1)).addAttribute("sizes", sizeService.getAllSizes());
    verify(model, times(1)).addAttribute("productSizes", productSizes);
    assertEquals("/backend-views/products-size", result);
}

@Test
public void testProductSize_productNotFound() {
    Long productId = 1L;

    when(productService.getProductById(productId)).thenReturn(Optional.empty());

    String result = adminController.productSize(productId, model, redirectAttributes);

    verify(productService, times(1)).getProductById(productId);
    verify(redirectAttributes, times(1)).addFlashAttribute("error", "Could not load product sizes.");
    assertEquals("redirect:/admin/ims", result);
}

@Test
public void testCreateProductSizePost_success() {
    Long productId = 1L;
    Long sizeId = 1L;
    Integer quantity = 5;

    Product product = new Product();
    product.setId(productId);

    Size size = new Size();
    size.setId(sizeId);

    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setProductId(productId);
    productSizeDTO.setSizeId(sizeId);
    productSizeDTO.setQuantity(quantity);

    when(productService.getProductById(productId)).thenReturn(Optional.of(product));
    when(sizeService.getSizeById(sizeId)).thenReturn(Optional.of(size));

    String result = adminController.createProductSizePost(productSizeDTO, redirectAttributes);

    verify(productService, times(1)).getProductById(productId);
    verify(sizeService, times(1)).getSizeById(sizeId);
    verify(productSizeService, times(1)).saveProductSize(any(ProductSize.class));
    verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Product size added successfully.");
    assertEquals("redirect:/admin/ims", result);
}

@Test
public void testCreateProductSizePost_failure() {
    Long productId = 1L;
    Long sizeId = 1L;
    Integer quantity = 5;

    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setProductId(productId);
    productSizeDTO.setSizeId(sizeId);
    productSizeDTO.setQuantity(quantity);

    when(productService.getProductById(productId)).thenReturn(Optional.empty());

    String result = adminController.createProductSizePost(productSizeDTO, redirectAttributes);

    verify(productService, times(1)).getProductById(productId);
    verify(redirectAttributes, times(1)).addFlashAttribute("errorMessage", "Could not add product size.");
    assertEquals("redirect:/admin/products/productSize/create", result);
}

@Test
    public void testDeleteProductSize_success() throws Exception {
        Long id = 1L;
        String expectedRedirectUrl = "redirect:/admin/ims";
        Mockito.doNothing().when(productSizeService).deleteProductSizeById(id);
        String result = adminController.deleteProductSize(id, redirectAttributes);
        assertEquals(expectedRedirectUrl, result);
        Mockito.verify(redirectAttributes).addFlashAttribute(eq("successMessage"), anyString());
    }
    
    @Test
    public void testDeleteProductSize_exception() throws Exception {
        Long id = 1L;
        String expectedRedirectUrl = "redirect:/admin/ims";
        Mockito.doThrow(new RuntimeException()).when(productSizeService).deleteProductSizeById(id);
        String result = adminController.deleteProductSize(id, redirectAttributes);
        assertEquals(expectedRedirectUrl, result);
        Mockito.verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
    }
    
    @Test
public void testUpdateProductSizeGet_success() throws Exception {
    Long id = 1L;
    ProductSize productSize = new ProductSize();
    Product product = new Product();
    Size size = new Size();
    product.setId(1L);
    size.setId(1L);
    productSize.setId(id);
    productSize.setProduct(product);
    productSize.setSize(size);
    productSize.setQuantity(10);
    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setId(id);
    productSizeDTO.setProductId(product.getId());
    productSizeDTO.setSizeId(size.getId());
    productSizeDTO.setQuantity(productSize.getQuantity());
    List<Product> products = Arrays.asList(product);
    List<Size> sizes = Arrays.asList(size);
    Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.of(productSize));
    Mockito.when(productService.getAllProduct()).thenReturn(products);
    Mockito.when(sizeService.getAllSizes()).thenReturn(sizes);
    String expectedViewName = "/backend-views/product-size-update";
    String result = adminController.updateProductSizeGet(id, model, redirectAttributes);
    assertEquals(expectedViewName, result);
    Mockito.verify(model).addAttribute(eq("productSizeDTO"), any(ProductSizeDTO.class));
    Mockito.verify(model).addAttribute(eq("products"), eq(products));
    Mockito.verify(model).addAttribute(eq("sizes"), eq(sizes));
}

@Test
public void testUpdateProductSizeGet_exception() throws Exception {
    Long id = 1L;
    Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.empty());
    String expectedRedirectUrl = "redirect:/admin/ims";
    String result = adminController.updateProductSizeGet(id, model, redirectAttributes);
    assertEquals(expectedRedirectUrl, result);
    Mockito.verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
}

@Test
public void testUpdateProductSizePost_success() throws Exception {
    Long id = 1L;
    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setId(id);
    productSizeDTO.setProductId(1L);
    productSizeDTO.setSizeId(1L);
    productSizeDTO.setQuantity(10);
    Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.of(new ProductSize()));
    Mockito.when(productService.getProductById(productSizeDTO.getProductId())).thenReturn(Optional.of(new Product()));
    Mockito.when(sizeService.getSizeById(productSizeDTO.getSizeId())).thenReturn(Optional.of(new Size()));
    String expectedRedirectUrl = "redirect:/admin/ims";
    String expectedSuccessMessage = "Product size updated successfully.";
    String result = adminController.updateProductSizePost(productSizeDTO, redirectAttributes);
    assertEquals(expectedRedirectUrl, result);
    Mockito.verify(productSizeService).saveProductSize(any(ProductSize.class));
    Mockito.verify(redirectAttributes).addFlashAttribute(eq("successMessage"), eq(expectedSuccessMessage));
}

@Test
public void testUpdateProductSizePost_invalidProductSizeId() throws Exception {
    Long id = 1L;
    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setId(id);
    productSizeDTO.setProductId(1L);
    productSizeDTO.setSizeId(1L);
    productSizeDTO.setQuantity(10);
    Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.empty());
    String expectedRedirectUrl = "redirect:/admin/ims";
    String expectedErrorMessage = "Could not update product size.";
    String result = adminController.updateProductSizePost(productSizeDTO, redirectAttributes);
    assertEquals(expectedRedirectUrl, result);
    Mockito.verify(productSizeService, Mockito.never()).saveProductSize(any(ProductSize.class));
    Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage", expectedErrorMessage);
}

public void testUpdateProductSizePost_invalidProductId() throws Exception {
Long id = 1L;
Long invalidProductId = 2L;
ProductSizeDTO productSizeDTO = new ProductSizeDTO();
productSizeDTO.setId(id);
productSizeDTO.setProductId(invalidProductId);
productSizeDTO.setSizeId(1L);
productSizeDTO.setQuantity(10);
ProductSize productSize = new ProductSize();
productSize.setId(id);
Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.of(productSize));
Mockito.when(productService.getProductById(invalidProductId)).thenReturn(Optional.empty());
String expectedRedirectUrl = "redirect:/admin/products/productSize/update/" + id;
String expectedErrorMessage = "Invalid product ID: " + invalidProductId;
String result = adminController.updateProductSizePost(productSizeDTO, redirectAttributes);
assertEquals(expectedRedirectUrl, result);
Mockito.verify(productSizeService, Mockito.never()).saveProductSize(any(ProductSize.class));
Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage", expectedErrorMessage);
}

public void testUpdateProductSizePost_invalidSizeId() throws Exception {
Long id = 1L;
Long invalidSizeId = 2L;
ProductSizeDTO productSizeDTO = new ProductSizeDTO();
productSizeDTO.setId(id);
productSizeDTO.setProductId(1L);
productSizeDTO.setSizeId(invalidSizeId);
productSizeDTO.setQuantity(10);
ProductSize productSize = new ProductSize();
productSize.setId(id);
Mockito.when(productSizeService.getProductSizeById(id)).thenReturn(Optional.of(productSize));
Mockito.when(productService.getProductById(productSizeDTO.getProductId())).thenReturn(Optional.of(new Product()));
Mockito.when(sizeService.getSizeById(invalidSizeId)).thenReturn(Optional.empty());
String expectedRedirectUrl = "redirect:/admin/products/productSize/update/" + id;
String expectedErrorMessage = "Invalid size ID: " + invalidSizeId;
String result = adminController.updateProductSizePost(productSizeDTO, redirectAttributes);
assertEquals(expectedRedirectUrl, result);
Mockito.verify(productSizeService, Mockito.never()).saveProductSize(any(ProductSize.class));
Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage", expectedErrorMessage);
}

/* End of ProductSize Crud Tests */

/* Start of Size Crud Tests */
@Test
    public void testSizes() {
        List<Size> sizes = Arrays.asList(new Size());
        Mockito.when(sizeService.getAllSizes()).thenReturn(sizes);
        String result = adminController.sizes(model, customUserDetail);
        assertEquals("/backend-views/size", result);
        Mockito.verify(model).addAttribute("size", sizes);
    }

    @Test
    public void testCreateSizeGet() {
        String result = adminController.createSizeGet(model, customUserDetail);
        assertEquals("/backend-views/size-create", result);
        Mockito.verify(model).addAttribute("size", new Size());
    }
 
    @Test
    public void testCreateSizePost() {
        Size size = new Size();
        size.setName("Test Size");
        
        doNothing().when(sizeService).saveSize(size);
        
        String result = adminController.createSizePost(size, model);
        
        verify(sizeService, times(1)).saveSize(size);
        assertEquals("redirect:/admin/size", result);
    }
    
    @Test
    public void testCreateSizePostWithDuplicateName() {
        Size size = new Size();
        size.setName("Test Size");
        
        doThrow(new DataIntegrityViolationException("")).when(sizeService).saveSize(size);
        
        String result = adminController.createSizePost(size, model);
        
        verify(sizeService, times(1)).saveSize(size);
        verify(model, times(1)).addAttribute(eq("errorMessage"), anyString());
        assertEquals("/backend-views/size-create", result);
    }
    @Test
    public void testDeleteSize() {
        Long id = 1L;
        String result = adminController.deleteSize(id, redirectAttributes);
        assertEquals("redirect:/admin/size", result);
        Mockito.verify(sizeService).deleteSize(id);
        Mockito.verify(redirectAttributes).addFlashAttribute("successMessage", "Size deleted successfully.");
    }

    @Test
    public void testDeleteSizeWithException() {
        Long id = 1L;
        DataIntegrityViolationException exception = new DataIntegrityViolationException("test");
        Mockito.doThrow(exception).when(sizeService).deleteSize(id);
        String result = adminController.deleteSize(id, redirectAttributes);
        assertEquals("redirect:/admin/size", result);
        Mockito.verify(sizeService).deleteSize(id);
        Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage", "Cannot delete this size as it is associated with one or more products.");
    }

    @Test
    public void testUpdateSizeGet() {
        Long id = 1L;
        Optional<Size> size = Optional.of(new Size());
        Mockito.when(sizeService.getSizeById(id)).thenReturn(size);
        String result = adminController.updateSizeGet(id, model);
        assertEquals("/backend-views/size-create", result);
        Mockito.verify(model).addAttribute("size", size.get());
    }

    @Test
    public void testUpdateSizeGetWithException() {
        Long id = 1L;
        Optional<Size> size = Optional.empty();
        Mockito.when(sizeService.getSizeById(id)).thenReturn(size);
        String result = adminController.updateSizeGet(id, model);
        assertEquals("404", result);
//Mockito.verifyZeroInteractions(model);
    }

/* End of Size Crud Tests */

/* Start of Order Crud Tests*/
@Test
public void testAdminOrdersWithKeyword() {
    // Given
    String keyword = "test";
    List<OrderDetails> orders = new ArrayList<>();
    orders.add(new OrderDetails());
    when(orderService.findByKeyword(keyword)).thenReturn(orders);
    
    // When
    String viewName = adminController.adminOrders(model, keyword, customUserDetail);
    
    // Then
    verify(orderService).findByKeyword(keyword);
    verify(model).addAttribute("orders", orders);
    assertEquals("/backend-views/admin-orders", viewName);
}

@Test
public void testAdminOrdersWithoutKeyword() {
    // Given
    List<OrderDetails> orders = new ArrayList<>();
    orders.add(new OrderDetails());
    when(orderService.getAllOrders()).thenReturn(orders);
    
    // When
    String viewName = adminController.adminOrders(model, null, customUserDetail);
    
    // Then
    verify(orderService).getAllOrders();
    verify(model).addAttribute("orders", orders);
    assertEquals("/backend-views/admin-orders", viewName);
}

@Test
public void testRemoveOrder() {
    // Given
    int id = 1;
    
    // When
    String viewName = adminController.removeOrder(id);
    
    // Then
    verify(orderService).removeProductById(id);
    assertEquals("redirect:/admin/orders", viewName);
}

@Test
public void testUpdateOrderGet() {
    // Given
    int id = 1;
    OrderDetails order = new OrderDetails();
    order.setId(id);
    Optional<OrderDetails> optionalOrder = Optional.of(order);
    OrderItem orderItem = new OrderItem();
    List<OrderItem> orderItemList = new ArrayList<>();
    orderItemList.add(orderItem);
    when(orderService.getOrderByorder_id(id)).thenReturn(optionalOrder);
    when(orderItemService.getAllOrderItemsByID(id)).thenReturn(orderItemList);
    Model model = mock(Model.class);
    CustomUserDetail customUserDetail = mock(CustomUserDetail.class);
    
    // When
    String viewName = adminController.updateOrderGet(id, model, customUserDetail);
    
    // Then
    verify(model).addAttribute("adminname", customUserDetail.getFirstname());
    verify(model).addAttribute(eq("orderDTO"), any(OrderDTO.class));
    verify(model).addAttribute("productsOrdered", orderItemList);
    assertEquals("/backend-views/orders-update", viewName);
}

@Test
public void testUpdateOrderGetRed() {
    // Given
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setId(1);
    orderDTO.setName("test");
    orderDTO.setEmail("test@test.com");
    orderDTO.setStatus(1);
    orderDTO.setTotal(100.0);
    List<OrderItem> orderItems = new ArrayList<>();
    orderDTO.setOrderItems(orderItems);
    OrderDetails order = new OrderDetails();
    doAnswer(invocation -> {
        OrderDetails savedOrder = invocation.getArgument(0);
        savedOrder.setId(1);
        savedOrder.setStatus(2);
        return null;
    }).when(orderService).saveOrder(any(OrderDetails.class));
        
    // When
    String viewName = adminController.updateOrderGetRed(orderDTO);
    
    // Then
    verify(orderService).saveOrder(any(OrderDetails.class));
    assertEquals("redirect:/admin/orders", viewName);
}

/* End of Order Crud Tests*/

@Test
public void testAdminCustomers() {
    // Create a test CustomUserDetail object
    User user = new User();
    CustomUserDetail customUserDetail = new CustomUserDetail(user);
    customUserDetail.setFirstname("admin");

    // Create a test list of CustomUserDetail objects
    List<User> customers = new ArrayList<>();
    User customer1 = new User();
    customer1.setId(1);
    customer1.setFirstname("John");
    customer1.setLastname("Doe");
    customers.add(customer1);
    User customer2 = new User();
    customer2.setId(2);
    customer2.setFirstname("Jane");
    customer2.setLastname("Doe");
    customers.add(customer2);

    // Mock the CustomUserDetailService and AuthenticationPrincipal objects
    Model model = mock(Model.class);
    when(customUserDetailService.getUsersByRole(2)).thenReturn(customers);

   CustomUserDetail auth = mock(CustomUserDetail.class);
    when(auth.getFirstname()).thenReturn("admin");

   
    // Call the controller method
    String result = adminController.adminCustomers(auth, model);

    // Verify the model attributes and view name
    assertEquals("/backend-views/customers", result);
    verify(model).addAttribute("adminname", "admin");
    verify(model).addAttribute("customers", customers);
}

@Test
public void testAdminIMS() {
    User user = new User();
    CustomUserDetail customUserDetail = new CustomUserDetail(user);
    customUserDetail.setFirstname("admin");

    List<ProductSize> productSizes = new ArrayList<>();
    productSizes.add(new ProductSize());

    when(productSizeService.getAllProductSizes()).thenReturn(productSizes);

    String result = adminController.adminIMS(customUserDetail, model);

    assertEquals("backend-views/products-size", result);
    verify(model).addAttribute("adminname", "admin");
    verify(model).addAttribute("productSizes", productSizes);
}


}

