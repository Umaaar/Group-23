package com.example.KingsMen.controller;

import com.example.KingsMen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    ProductService productService;
   @GetMapping("/admin")
   public String adminDashboard(){
       return "/backend-views/admin-dashboard";
   }

   @GetMapping("/admin/products")
    public String adminProducts(Model model){
       model.addAttribute("products", productService.getAllProducts());
       return "/backend-views/admin-products";
   }

    @GetMapping("/admin/orders")
    public String adminOrders(){ return "/backend-views/admin-orders";}

    @GetMapping("/admin/accounts")
    public String adminAccounts(){ return "/backend-views/admin-accounts";}

    @GetMapping("/admin/accounts/add")
    public String adminAddProduct(){ return "/backend-views/admin-addProduct";}

    @GetMapping("/admin/categories")
    public String adminCat(){ return "/backend-views/admin-categories";}

}
