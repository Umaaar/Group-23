package org.example.kingsmen;

import org.example.controller.WishListController;
import org.example.model.CustomUserDetail;
import org.example.model.Product;
import org.example.model.User;
import org.example.model.WishList;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.service.WishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private WishListService wishListService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private CustomUserDetail customUserDetail;

    @Mock
    private User user;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private WishListController wishListController;

    @Test
    public void testAddToWishListProductAlreadyInWishList() {
        // setup
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        List<WishList> wishList = new ArrayList<>();
        WishList existingWishListItem = new WishList(user, product);
        wishList.add(existingWishListItem);

        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(customUserDetail.getUser()).thenReturn(user);
        when(wishListService.readWishList(user.getId())).thenReturn(wishList);

        // execute
        String viewName = wishListController.addToWishList(1L, customUserDetail, redirectAttributes);

        // verify
        assertEquals("redirect:/wishlist", viewName);
        verify(wishListService, never()).createWishlist(any(WishList.class));
        verify(redirectAttributes).addFlashAttribute("errorMsg", "Product is already in wishlist!");
    }

    @Test
    public void testAddToWishListProductNotInWishList() {
    // setup
    Product product = new Product();
    product.setId(1L);
    product.setName("Product 1");

    List<WishList> wishList = new ArrayList<>();

    when(productService.getProductById(1L)).thenReturn(Optional.of(product));
    when(customUserDetail.getUser()).thenReturn(user);
    when(wishListService.readWishList(user.getId())).thenReturn(wishList);

    // execute
    String viewName = wishListController.addToWishList(1L, customUserDetail, redirectAttributes);

    // verify
    assertEquals("redirect:/wishlist", viewName);
    verify(wishListService).createWishlist(any(WishList.class));
    verify(redirectAttributes).addFlashAttribute("successMsg", "Product added to wishlist!");
    }

    @Test
    public void testAddToWishListInvalidProduct() {
    // setup
    Long invalidProductId = 99L;

    when(productService.getProductById(invalidProductId)).thenReturn(Optional.empty());

    // execute
    String viewName = wishListController.addToWishList(invalidProductId, customUserDetail, redirectAttributes);

    // verify
    assertEquals("redirect:/wishlist", viewName);
    verify(wishListService, never()).createWishlist(any(WishList.class));
    verify(redirectAttributes).addFlashAttribute(eq("errorMsg"), any(String.class));
    }

    @Test
    public void testRemoveFromWishList() {
    // setup
    Long productIdToRemove = 1L;
    when(customUserDetail.getUser()).thenReturn(user);

    // execute
    String viewName = wishListController.wishlistItemRemove(productIdToRemove, customUserDetail, redirectAttributes);

    // verify
    assertEquals("redirect:/wishlist", viewName);
    verify(wishListService).deleteFromWishList(user.getId(), productIdToRemove);
    verify(redirectAttributes).addFlashAttribute("successMsg", "Product removed from wishlist!");
    }

 



}