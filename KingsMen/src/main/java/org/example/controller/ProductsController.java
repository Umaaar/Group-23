package org.example.controller;


import org.example.model.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
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

    @Autowired 
    ProductSizeService productSizeService;

    @Autowired
    SizeService sizeService;


    @GetMapping("/product")
    public String getProductsPage(Model model,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productService.getProductsByCategoryId(categoryId);
        } else if (keyword != null) {
            products = productService.findByKeyword(keyword);
        } else {
            products = productService.getAllProduct();
        }
        if (sort.equals("desc")) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else {
            products.sort(Comparator.comparing(Product::getPrice));
        }
        if (products.isEmpty()) {
            List<Product> recommendedProducts = productService.getRandomProducts(4);
            model.addAttribute("keyword", keyword); // add the keyword to the model
            if (recommendedProducts.isEmpty()) {
                model.addAttribute("products", Collections.emptyList());
                model.addAttribute("categories", catagoryService.getAllCategory());
    
            } else {
                model.addAttribute("products", recommendedProducts);
                model.addAttribute("categories", catagoryService.getAllCategory());
            }
            return "frontend-views/product-not-found";
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", catagoryService.getAllCategory());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword); // add the keyword to the model
        return "frontend-views/product-page";
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
          model.addAttribute("productSizes", productSizeService.getProductSizesByProductId(id));
          model.addAttribute("sizes", sizeService.getAllSizes());
          model.addAttribute("categories", catagoryService.getAllCategory());

          return "frontend-views/product-detail-page";
      }
}


  /*private List<Product> getAllProducts(){
    return productService.findAllByOrderByIdAsc();
    

  }*/


