package com.example.KingsMen.controller;

import com.example.KingsMen.model.Catagory;
import com.example.KingsMen.service.CatagoryService;
import com.example.KingsMen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    CatagoryService catagoryService;

   @GetMapping("/admin")
   public String adminHome(){
       return "/backend-views/admin-index";
   }
   
   @GetMapping("/admin/categories")
   public String getCat(Model model){ 
    model.addAttribute("categories", catagoryService.getAllCategory());
    return "/backend-views/categories";
   }

    @GetMapping("/admin/categories/create")
    public String postCreateCat(Model model){ 
        model.addAttribute("category", new Catagory());
     return "/backend-views/category-create";
    }

    @PostMapping("/admin/categories/create")
    public String adminCreateCat(@ModelAttribute("category") Catagory category){ 
        catagoryService.addCategory(category);
        System.out.println("it works");
     return "redirect:/admin/categories";
    }







   @GetMapping("/admin/products")
    public String adminProducts(Model model){
       model.addAttribute("products", productService.findAllByOrderByIdAsc());
       return "/backend-views/products";
   }

   @GetMapping("/admin/products/create")
   public String adminCreateProduct(){ 
    return "/backend-views/products-create";
   }

   

    @GetMapping("/admin/orders")
    public String adminOrders(){ return "/backend-views/admin-orders";
}

    @GetMapping("/admin/accounts")
    public String adminAccounts(){ return "/backend-views/admin-accounts";
}

    @GetMapping("/admin/accounts/add")
    public String adminAddProduct(){ return "/backend-views/admin-addProduct";
}




}
