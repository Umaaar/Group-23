package org.example.controller;

 import org.example.model.CustomUserDetail;
 import org.example.model.Product;
 import org.example.model.WishList;
import org.example.service.CategoryService;
import org.example.service.ProductService;
 import org.example.service.WishListService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.annotation.AuthenticationPrincipal;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.util.List;

 @Controller
 public class WishListController {

     @Autowired
     private ProductService productService;

     @Autowired
     CategoryService catagoryService;

     @Autowired
     private WishListService wishListService;
     @PostMapping("/addToWishList/{id}")
     public String addToWishList(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail authentication, RedirectAttributes redirectAttributes) {
         try {
             Product product = productService.getProductById(id)
                     .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

             List<WishList> wishList = wishListService.readWishList(authentication.getUser().getId());
             for(WishList w: wishList){
                 if(w.getProduct().getId().equals(product.getId())){
                     redirectAttributes.addFlashAttribute("errorMsg", "Product is already in wishlist!");
                     return "redirect:/wishlist";
                 }
             }

             // Only create a new wish list item if the product is not already in the user's wish list
             WishList nWishList = new WishList();
             nWishList.setUser(authentication.getUser());
             nWishList.setProduct(product);
             wishListService.createWishlist(nWishList);

             redirectAttributes.addFlashAttribute("successMsg", "Product added to wishlist!");
         } catch (IllegalArgumentException e) {
             redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
         } catch (Exception e) {
             redirectAttributes.addFlashAttribute("errorMsg", "Unexpected error occurred.");
         }

         return "redirect:/wishlist";
     }


     @GetMapping("/wishlist")
     public String wishlistGet(@AuthenticationPrincipal CustomUserDetail authentication, Model model, HttpServletResponse response) throws IOException {
         if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

             return "redirect:/admin";

         } else {
             List<WishList> wishList = wishListService.readWishList(authentication.getUser().getId());

             model.addAttribute("name", authentication.getFirstname());
             model.addAttribute("email", authentication.getEmail());
             model.addAttribute("wishList", wishList);
             model.addAttribute("categories", catagoryService.getAllCategory());


             return "/frontend-views/wishlist-page";
         }

     }



     @PostMapping("/wishlist/removeItem/{id}")
     public String wishlistItemRemove(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail authentication, RedirectAttributes redirectAttributes) {
         wishListService.deleteFromWishList(authentication.getUser().getId(), id);
         redirectAttributes.addFlashAttribute("successMsg", "Product removed from wishlist!");

         return "redirect:/wishlist";
     }


 }