package org.example.controller;


import org.example.model.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;



@Controller
public class ProductsController {

    @Autowired
    CategoryService catagoryService;

    @Autowired
    ProductService productService;


    @GetMapping("/product")
    public String getProductsPage(Model model) {
      List<Product> products = productService.getAllProduct();
      model.addAttribute("products", products);
      model.addAttribute("categories", catagoryService.getAllCategory());
      return "/frontend-views/product-page";
  }

      @GetMapping("/product/{categoryId}")
      public String getProductsByCategory(Model model, @PathVariable("categoryId") int categoryId) {
          List<Product> products = productService.getProductsByCategoryId(categoryId);
          model.addAttribute("products", products);
          model.addAttribute("categories", catagoryService.getAllCategory());
          return "/frontend-views/product-page";
      }



      @GetMapping("/product/product-detail/{id}")
      public String getProductDetailPage(Model model, @PathVariable("id") Long id) {
          Optional<Product> product = productService.getProductById(id);
          model.addAttribute("product", product.orElse(null));
          return "frontend-views/product-detail-page";
      }
}


  /*private List<Product> getAllProducts(){
    return productService.findAllByOrderByIdAsc();
    

  }*/


