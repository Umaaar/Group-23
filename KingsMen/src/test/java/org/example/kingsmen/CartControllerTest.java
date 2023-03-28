package org.example.kingsmen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.example.controller.CartController;
import org.example.dto.ProductDTO;
import org.example.global.GlobalData;
import org.example.model.CustomUserDetail;
import org.example.model.Product;
import org.example.model.ProductSize;
import org.example.model.Size;
import org.example.model.User;
import org.example.service.OrderItemService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

public class CartControllerTest {

    private ProductService productService;
    private OrderService orderService;
    private ProductSizeService productSizeService;
    private OrderItemService orderItemService;
    private SizeService sizeService;
    private RedirectAttributes redirectAttributes;
    private CartController cartController;
    private Model model;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        orderService = mock(OrderService.class);
        productSizeService = mock(ProductSizeService.class);
        orderItemService = mock(OrderItemService.class);
        sizeService = mock(SizeService.class);
        redirectAttributes = mock(RedirectAttributes.class);
        cartController = new CartController();
        cartController.setProductService(productService);
        cartController.setOrderService(orderService);
        cartController.setProductSizeService(productSizeService);
        cartController.setOrderItemService(orderItemService);
        cartController.setSizeService(sizeService);
        model = mock(Model.class);
    }

    @Test
    public void testAddToCart() {
        // Arrange
        GlobalData.cart.clear();
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");
        product.setPrice(10.0);
        when(productService.getProductById(id)).thenReturn(Optional.of(product));

        // Act
        String viewName = cartController.addToCart(id);

        // Assert
        assertEquals("redirect:/cart", viewName);
        List<Product> cart = GlobalData.cart;
        assertEquals(1, GlobalData.cart.size());
        assertEquals(product, cart.get(0));
    }

    @Test
    public void testCartGet() {
        // Arrange
        GlobalData.cart = new ArrayList<>();
        User user = new User();
        user.setFirstname("Test User");
        user.setEmail("test@test.com");
        CustomUserDetail authentication = new CustomUserDetail(user);
        GlobalData.cart = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product 1");
        product1.setPrice(10.0);
        product1.setQuantity(1);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Test Product 2");
        product2.setPrice(20.0);
        product2.setQuantity(1);
        GlobalData.cart.add(product1);
        GlobalData.cart.add(product2);

        // Act
        String viewName = cartController.cartGet(authentication, model);

        // Assert
        assertEquals("/frontend-views/cart-page", viewName);
    }

    @Test
    public void testCartItemRemove() {
        // Arrange
        int index = 0;
        int initialSize = GlobalData.cart.size();
        Product product1 = GlobalData.cart.get(0);

        // Act
        String viewName = cartController.cartItemRemove(index, model);

        // Assert
        int finalSize = GlobalData.cart.size();
        assertEquals(initialSize - 1, finalSize);
        assertFalse(GlobalData.cart.contains(product1));
        assertEquals("redirect:/cart", viewName);
    }

}
