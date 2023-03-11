package org.example.controller;

import org.example.model.Product;
import org.example.model.CustomUserDetail;
import org.example.model.OrderDetails;
import org.example.model.OrderItem;
import org.example.dto.ProductDTO;
import org.example.global.GlobalData;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.OrderDTO;



@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        model.addAttribute("name",authentication.getFirstname());
        model.addAttribute("email",authentication.getEmail());
        return "/frontend-views/cart-page";
    }

    @PostMapping("/checkout")
    public String createOrder(OrderDTO orderDTO, RedirectAttributes redirectAttributes)  throws IOException{
        if(GlobalData.cart.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Cart is Empty");
        }else{
            OrderDetails order = new OrderDetails();
            order.setId(orderDTO.getId());
            order.setEmail(orderDTO.getEmail());
            order.setName(orderDTO.getName());
            List<OrderItem> items = new ArrayList<>();
            String sentence = "";
            for (Product item : GlobalData.cart) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(item.getId());
                orderItem.setName(item.getName());
                orderItem.setSize(item.getSize());
                orderItem.setQuantity(item.getStock());
                orderItem.setStock(orderItem.getStock()-item.getStock());
                orderItem.setPrice(item.getPrice()); // Replace with the price of the product at the time of the order
                String orderProducts = sentence += "Product:" + orderItem.getId() + " Size:" + orderItem.getSize() + " Quantity:" + orderItem.getQuantity() + ", ";
                order.setOrder_products(orderProducts);
                System.out.println(orderProducts);
                items.add(orderItem);
                productService.decreasingStock(orderItem.getId(), orderItem.getQuantity()); //decreasing stock
            }
            order.setStatus(1);
            Long total = (long) GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum();
            order.setTotal(total);
            orderService.addOrder(order);
            GlobalData.cart.removeAll(GlobalData.cart);
            redirectAttributes.addFlashAttribute("successMessage", "Order Placed Successfully!");
        };
        return "redirect:/cart"; 
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index, Model model){
        GlobalData.cart.remove(index);
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "redirect:/cart";
    }

    @PostMapping("/addToCart/{id}")
    public String dropdown(@PathVariable Long id, ProductDTO dropdown, RedirectAttributes redirectAttributes){
        Product item = productService.getProductById(id).get();
        if(item.getStock() <= 0){
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, Item Out of Stock");
        }else{
            item.setSize(dropdown.getSize());
            item.setStock(dropdown.getStock());
            item.setPrice(item.getPrice() * item.getStock());
            GlobalData.cart.add(productService.getProductById(id).get());
            redirectAttributes.addFlashAttribute("successMessage", "Item Added To Cart!");
        }
    
        return "redirect:/product/product-detail/{id}";
    }

}
