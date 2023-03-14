package org.example.controller;


import org.example.model.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



@Controller
public class ProductsController {

    @Autowired
    CategoryService catagoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/product")
public String getProductsPage(Model model,
    @RequestParam(defaultValue = "asc") String sort,
    @RequestParam(required = false) String keyword) {
    List<Product> products;
    if (keyword != null) {
        products = productService.findByKeyword(keyword);
        if (sort.equals("asc")) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else {
            products.sort(Comparator.comparing(Product::getPrice));
        }
    } else {
        products = productService.getAllProduct();
        if (sort.equals("desc")) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else {
            products.sort(Comparator.comparing(Product::getPrice));
        }
    }
    model.addAttribute("products", products);
    model.addAttribute("categories", catagoryService.getAllCategory());
    boolean notFound = products.isEmpty() && keyword != null;
    if (notFound) {
        model.addAttribute("keyword", keyword);
        return "frontend-views/product-not-found";
    } else {
        model.addAttribute("notFound", false);
        return "frontend-views/product-page";
    }
}



      @GetMapping("/product/{categoryId}")
      public String getProductsByCategory(Model model, 
      @PathVariable("categoryId") int categoryId, 
      @RequestParam(defaultValue = "asc") String sort) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        if (sort.equals("desc")) {
            Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
        } else {
            Collections.sort(products, Comparator.comparing(Product::getPrice));
        }
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

      @GetMapping("/product/search")
      public String searchProducts(Model model, @RequestParam String q) {
      List<Product> products = productService.findByKeyword(q);
      model.addAttribute("products", products);
      model.addAttribute("categories", catagoryService.getAllCategory());
      return "/frontend-views/product-page";
    }

    @GetMapping("/product/not-found")
    public String getProductNotFoundPage(Model model, @RequestParam String productName) {
      model.addAttribute("productName", productName);
      return "/frontend-views/product-not-found";
}


}


  /*private List<Product> getAllProducts(){
    return productService.findAllByOrderByIdAsc();
    

  }*/


