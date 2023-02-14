package com.example.KingsMen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.KingsMen.service.ProductService;

@Controller
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public String getProductsPage() {
        // List<Product> products = productService.getAllProducts();
        // model.addAttribute("products", products);
        return "/frontend-views/product-page";
    }

    @GetMapping("/product/product-detail")
    public String productDetail() {
    return "/frontend-views/product-detail-page";
  }


}

