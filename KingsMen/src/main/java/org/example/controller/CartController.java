package org.example.controller;

import org.example.model.Product;
import org.example.dto.ProductDTO;
import org.example.global.GlobalData;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        // Stock for each cart item
        // for (Product p : GlobalData.cart) {
        //     Long pId = p.getId();
        //     int productStock = productService.getProductById(pId).get().getStock();
        //     model.addAttribute("productStock", productStock);

        // }
        return "/frontend-views/cart-page";
    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index, Model model){
        GlobalData.cart.remove(index);
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        return "checkout";

    }

    @PostMapping("/addToCart/{id}")
    public String dropdown(@PathVariable Long id, ProductDTO dropdown, Model model){
        Product item = productService.getProductById(id).get();
       
        item.setSize(dropdown.getSize());
        item.setStock(dropdown.getStock());
        item.setPrice(item.getPrice() * item.getStock());
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

}
