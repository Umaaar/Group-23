package com.example.KingsMen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
   @GetMapping("/admin")
   public String adminDashboard(){
       return "/backend-views/admin-dashboard";
   }

   @GetMapping("/admin/products")
    public String adminProducts(){ return "/backend-views/admin-products";}

    @GetMapping("/admin/orders")
    public String adminOrders(){ return "/backend-views/admin-orders";}

    @GetMapping("/admin/accounts")
    public String adminAccounts(){ return "/backend-views/admin-accounts";}

    @GetMapping("/admin/accounts")
    public String adminAddProduct(){ return "/backend-views/admin-addProduct";}

    @GetMapping("/admin/categories")
    public String adminCat(){ return "/backend-views/admin-categories";}

}
