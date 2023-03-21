package org.example.controller;

import org.example.model.*;
import org.example.dto.ProductDTO;
import org.example.global.GlobalData;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
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
    @Autowired
    ProductSizeService productSizeService;

    @Autowired
    SizeService sizeService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        model.addAttribute("name",authentication.getFirstname());
        model.addAttribute("email",authentication.getEmail());
        return "/frontend-views/cart-page";
    }

    
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index, Model model){
        GlobalData.cart.remove(index);
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        return "redirect:/cart";
    }

    @PostMapping("/addToCart/{id}")
    public String dropdown(@PathVariable Long id, ProductDTO dropdown, RedirectAttributes redirectAttributes ){
        Product item = productService.getProductById(id).get();
        Product newItem = new Product();



        newItem.setId(item.getId());
        System.out.println(item.getId());
        newItem.setName(item.getName());
        Category category = item.getCategory();

        newItem.setCategory(category);
        newItem.setDescription(item.getDescription());
        newItem.setPrice(item.getPrice());
        newItem.setStock(item.getStock());
        newItem.setImageName(item.getImageName());
        
        if(item.getStock() <= 0){
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, Item Out Of Stock");
        }else if(dropdown.getStock() > item.getStock()){
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, Max Quantity For This Item Is " + item.getStock());
        }else{
            List<ProductSize> s = new ArrayList<>();
//            System.out.println("this iis the id" + sizeID);
            System.out.println(dropdown.getProductSizeIds());
            for (Long sizeids : dropdown.getProductSizeIds()) {
                    System.out.println(sizeids);
//                    if ( sizeids == dropdown.getProductSizeIds()){
                        ProductSize size = productSizeService.getProductSizeById(sizeids).get();

                       System.out.println(sizeService.getSizeById(sizeids).get().getName());
                        newItem.setSize(sizeService.getSizeById(sizeids).get().getName());

//                    }
//                    else{
//                        sizeids++;
//                    }


            }
//            for (ProductSize index: s) {
//
//                System.out.println(index.getSize().getName());
//
//            }
            item.setQuantity(dropdown.getStock());
            item.setPrice(item.getPrice() * item.getQuantity());
//            System.out.println(s.size());
            newItem.setQuantity(dropdown.getStock());
            newItem.setPrice(item.getPrice() * item.getQuantity());

            GlobalData.cart.add(newItem);

            redirectAttributes.addFlashAttribute("successMessage", "Item Added To Cart!");
        }
        return "redirect:/product/product-detail/{id}";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "redirect:/cart";
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
                String sizeName;
                for(ProductSize sizes : item.getProductSizes()){
                    sizeName = sizes.getSize().getName();
                    orderItem.setSizes(sizeName);
                }
                orderItem.setQuantity(item.getStock());
                orderItem.setStock(orderItem.getStock()-item.getStock());
                orderItem.setPrice(item.getPrice()); // Replace with the price of the product at the time of the order
                String orderProducts = sentence += "Product:" + orderItem.getId() + " Size:" + orderItem.getSizes() + " Quantity:" + orderItem.getQuantity() + ", ";
                order.setOrder_products(orderProducts);
                System.out.println(orderProducts);
                items.add(orderItem);
                productService.decreasingStock(orderItem.getId(), orderItem.getQuantity()); //decreasing stock
            }
            order.setStatus(1);
            Double total = GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
            order.setTotal(total);
            orderService.addOrder(order);
            GlobalData.cart.removeAll(GlobalData.cart);
            redirectAttributes.addFlashAttribute("successMessage", "Order Placed Successfully!");
        };
        return "redirect:/cart"; 
    }


}
