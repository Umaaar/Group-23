package com.example.KingsMen.controller;

import com.example.KingsMen.dto.ProductDTO;
import com.example.KingsMen.dto.SizeDTO;
import com.example.KingsMen.model.Category;
import com.example.KingsMen.service.CatagoryService;
import com.example.KingsMen.service.ProductService;
import com.example.KingsMen.service.SizeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    CatagoryService catagoryService;

    @Autowired
    SizeService sizeService;

   @GetMapping("/admin")
   public String adminHome(){
       return "/backend-views/admin-index";
   }
   
/* ---------------------------------------------------Category CRUD Mapping ------------------------------------------------- */
   @GetMapping("/admin/categories")
   public String getCat(Model model){ 
    model.addAttribute("categories", catagoryService.getAllCategory());
       System.out.println(catagoryService.getAllCategory());
    return "/backend-views/categories";
   }

    @GetMapping("/admin/categories/create")
    public String postCreateCat(Model model){
        model.addAttribute("category", new Category());
     return "/backend-views/category-create";
    }

    @PostMapping("/admin/categories/create")
    public String adminCreateCat(@ModelAttribute("category") Category category){
        catagoryService.addCategory(category);
        System.out.println("it works");
     return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        catagoryService.removeCategoryById(id);
        return "redirect:/admin/categories";

    }

    @GetMapping("/admin/categories/update/{id}")
    public String editCategory(@PathVariable int id, Model model){
        Optional<Category> category = catagoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "/backend-views/category-create";
        }else{
            return "404";
        }
    }
/* --------------------------------------------------- End of Category CRUD Mapping ----------------------------------------------- */

/* --------------------------------------------------- Product CRUD Mapping --------------------------------------------------------*/

@GetMapping("/admin/products")
public String products(Model model){
   model.addAttribute("products", productService.getAllProduct());
   return "/backend-views/products";
}

@GetMapping("/admin/products/create")
public String createProducts(Model model){
   model.addAttribute("productDTO", new ProductDTO());
   model.addAttribute("categories", catagoryService.getAllCategory());
   return "/backend-views/products-create";
}





/* --------------------------------------------------- End of Product CRUD Mapping ------------------------------------------------- */

/* --------------------------------------------------- Size CRUD Mapping --------------------------------------------------------*/

@GetMapping("/admin/size")
public String size(Model model){
   model.addAttribute("size", sizeService.getAllSizes());
   return "/backend-views/size";
}

@GetMapping("/admin/size/create")
public String createSize(Model model){
   model.addAttribute("sizeDTO", new SizeDTO());
   model.addAttribute("categories", catagoryService.getAllCategory());
   return "/backend-views/size-create";
}




/* --------------------------------------------------- End of Size CRUD Mapping --------------------------------------------------------*/







   


   

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
